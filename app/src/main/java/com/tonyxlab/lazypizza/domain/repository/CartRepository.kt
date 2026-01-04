package com.tonyxlab.lazypizza.domain.repository

import com.tonyxlab.lazypizza.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    val cartItems: Flow<List<CartItem>>

    suspend fun addItem(cartItem: CartItem)
    suspend fun removeItem(cartItem: CartItem)
    suspend fun updateCount(cartItem: CartItem, newCount: Int)
    suspend fun clear()
    suspend fun clearAuthenticatedCart()

}