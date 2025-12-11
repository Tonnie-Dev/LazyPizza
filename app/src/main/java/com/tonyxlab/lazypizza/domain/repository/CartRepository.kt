package com.tonyxlab.lazypizza.domain.repository

import com.tonyxlab.lazypizza.domain.model.CartItem
import kotlinx.coroutines.flow.StateFlow

interface CartRepository {
    val cartItems: StateFlow<List<CartItem>>

    fun addItem(cartItem: CartItem)
    fun removeItem(cartItem: CartItem)
    fun updateCount(cartItem: CartItem, newCount: Int)
    fun clear()
}