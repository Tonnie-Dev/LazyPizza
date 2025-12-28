package com.tonyxlab.lazypizza.domain.firebase

sealed interface AuthState {
    data object Unauthenticated : AuthState
    data class OtpCodeSent(val verificationId: String, val resendToken: Any? = null) : AuthState
    data object Authenticated : AuthState
    data object Loading : AuthState
    data class Error(val message: String) : AuthState
}