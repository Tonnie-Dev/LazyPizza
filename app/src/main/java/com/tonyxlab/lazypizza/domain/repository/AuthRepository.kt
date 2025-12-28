package com.tonyxlab.lazypizza.domain.repository

import android.app.Activity
import com.tonyxlab.lazypizza.domain.firebase.AuthState
import com.tonyxlab.lazypizza.domain.model.AuthUser
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    val authState: StateFlow<AuthState>
    val currentUser: AuthUser?

    fun startLogin(phoneNumber:String, activity: Activity)
    fun verifyCode(otpCode:String,verificationId: String)
    fun logout()
}