package com.tonyxlab.lazypizza.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.tonyxlab.lazypizza.data.local.datastore.DataStore
import com.tonyxlab.lazypizza.domain.repository.CartIdProvider

class CartIdProviderImpl(
    private val firebaseAuth: FirebaseAuth,
    private val dataStore: DataStore
) : CartIdProvider {

    @Volatile
    private var cachedCartId: String = GUEST_ID

    override val currentCartId: String
        get() = firebaseAuth.currentUser?.uid
            ?: cachedCartId

   override suspend fun onLogin(userId: String) {
        cachedCartId = userId
        dataStore.setUserId(userId)
    }

   override suspend fun logout() {
        cachedCartId = GUEST_ID
        dataStore.setUserId(GUEST_ID)
    }

    override suspend fun hydrateFromDisk() {
        dataStore.getUserId().collect { id ->
            cachedCartId = id
        }
    }

    companion object {
        const val GUEST_ID = "GUEST"
    }
}
