package com.tonyxlab.lazypizza.data.repository

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.tonyxlab.lazypizza.domain.firebase.AuthState
import com.tonyxlab.lazypizza.domain.model.AuthUser
import com.tonyxlab.lazypizza.domain.model.toAuthUser
import com.tonyxlab.lazypizza.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AuthRepositoryImpl(private val firebaseAuth: FirebaseAuth) : AuthRepository {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    override val authState = _authState.asStateFlow()

    override val currentUser: AuthUser?
        get() = firebaseAuth.currentUser?.toAuthUser()

    init { observeAuthState() }

    override fun startLogin(phoneNumber: String, activity: Activity) {

        _authState.update { AuthState.Loading }

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                _authState.update {
                    AuthState.OtpCodeSent(
                            verificationId = verificationId,
                            resendToken = token
                    )
                }
            }

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                firebaseAuth.signInWithCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {

                Timber.tag("AuthRepositoryImpl").i("Exception: $e")
                _authState.update {
                    AuthState.Error(e.localizedMessage ?: "Verification Failed")
                }
            }
        }

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(callbacks)
                .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun verifyCode(otpCode: String, verificationId: String) {
        _authState.update { AuthState.Loading }
        val credential = PhoneAuthProvider.getCredential(
                verificationId,
                otpCode
        )

        firebaseAuth.signInWithCredential(credential)
                .addOnFailureListener { e ->
                    _authState.update {
                        AuthState.Error(
                                message = e.localizedMessage ?: "Invalid Code"
                        )
                    }
                }
    }

    override fun logout() {
        firebaseAuth.signOut()
        _authState.update { AuthState.Unauthenticated }
    }

    private fun observeAuthState() {

        firebaseAuth.addAuthStateListener { auth ->

            if (auth.currentUser != null)
                _authState.value = AuthState.Authenticated
            else
                _authState.value = AuthState.Unauthenticated
        }
    }

   /* private fun observeAuthState2() {

        if (firebaseAuth.currentUser != null) {

            _authState.value = AuthState.Authenticated
        } else {
            _authState.value = AuthState.Unauthenticated
        }
    }*/
}