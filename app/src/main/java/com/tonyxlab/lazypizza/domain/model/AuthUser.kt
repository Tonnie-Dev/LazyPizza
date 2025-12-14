package com.tonyxlab.lazypizza.domain.model

import com.google.firebase.auth.FirebaseUser

data class AuthUser(
    val userId: String,
    val phoneNumber: String?
)

fun FirebaseUser.toAuthUser(): AuthUser{
    return AuthUser(
            userId = uid,
            phoneNumber = phoneNumber
    )
}