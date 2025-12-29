package com.tonyxlab.lazypizza.domain.firebase

import com.google.firebase.auth.PhoneAuthProvider

sealed interface AuthState {
    data object Unauthenticated : AuthState
    data class OtpCodeSent(val verificationId: String, val resendToken: PhoneAuthProvider.ForceResendingToken? = null) : AuthState
    data object Authenticated : AuthState
    data object Loading : AuthState
    data class Error(val message: String) : AuthState
}