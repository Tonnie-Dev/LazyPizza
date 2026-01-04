package com.tonyxlab.lazypizza.domain.repository

interface CartIdProvider {

    val currentCartId: String

   suspend fun onLogin(userId: String)

    suspend fun logout()

    suspend fun hydrateFromDisk()
}