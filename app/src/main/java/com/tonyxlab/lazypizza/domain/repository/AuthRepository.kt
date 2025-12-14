package com.tonyxlab.lazypizza.domain.repository

import com.tonyxlab.lazypizza.domain.firebase.AuthState
import com.tonyxlab.lazypizza.domain.model.AuthUser
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    val authState: StateFlow<AuthState>
    val currentUser: AuthUser?

    fun startLogin(phoneNumber:String)
    fun verifyCode(otpCode:String,verificationId: String)
    fun logout()
}