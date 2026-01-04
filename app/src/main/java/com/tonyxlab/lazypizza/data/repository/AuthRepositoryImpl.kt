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
import com.tonyxlab.lazypizza.domain.repository.CartIdProvider
import com.tonyxlab.lazypizza.domain.repository.CartRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val cartRepository: CartRepository,
    private val cartIdProvider: CartIdProvider,
    private val appScope: CoroutineScope

) : AuthRepository {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    override val authState = _authState.asStateFlow()

    override val currentUser: AuthUser?
        get() = firebaseAuth.currentUser?.toAuthUser()

    init {
        observeAuthState()
    }

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

                credential.smsCode?.let { code ->
                    _authState.update { AuthState.AutoVerified(code) }
                }
                firebaseAuth.signInWithCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {

                _authState.update {
                    AuthState.Error(e.localizedMessage ?: "Verification Failed")
                }
            }
        }

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(0L, TimeUnit.SECONDS)
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

    override fun resendOtp(
        phoneNumber: String,
        resendToken: PhoneAuthProvider.ForceResendingToken,
        activity: Activity
    ) {
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
                _authState.update {
                    AuthState.Error(e.localizedMessage ?: "Resend failed")
                }
            }

        }

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(0L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(callbacks)
                .setForceResendingToken(resendToken) // ⭐ THIS is the magic
                .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun logout() {

        val userId = firebaseAuth.currentUser?.uid
        Timber.tag("AuthRepo")
                .i("userId is $userId")
        appScope.launch {

            if (userId != null) {
                Timber.tag("AuthRepo").i("clear called")
                cartRepository.clearAuthenticatedCart(userId)

                Timber.tag("AuthRepo").i("clear called")
            }

            firebaseAuth.signOut()
            cartIdProvider.logout()

            _authState.update { AuthState.Unauthenticated }
        }
    }

    private fun observeAuthState() {

        firebaseAuth.addAuthStateListener { auth ->

            val user = auth.currentUser

            appScope.launch {
                if (user != null) {
                    _authState.value = AuthState.Authenticated
                    cartIdProvider.onLogin(user.uid)   // ✅ safe now
                } else {
                    _authState.value = AuthState.Unauthenticated
                    cartIdProvider.logout()          // ✅ safe now
                }
            }
        }
    }
}
