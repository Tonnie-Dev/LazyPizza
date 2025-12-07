package com.tonyxlab.lazypizza.data.repository

import com.tonyxlab.lazypizza.domain.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartRepositoryImpl {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    fun addItem(item: CartItem) {
        val currentCartItemsList = _cartItems.value.toMutableList()

        val existingIndex = currentCartItemsList.indexOfFirst { it.id == item.id }

        if (existingIndex >= 0) {
            val updated = currentCartItemsList[existingIndex].copy(
                    counter = currentCartItemsList[existingIndex].counter + item.counter
            )
            currentCartItemsList[existingIndex] = updated
        } else {
            currentCartItemsList.add(item)
        }

        _cartItems.value = currentCartItemsList
    }

    fun updateQuantity(itemId: Long, newCount: Int) {
        _cartItems.value = _cartItems.value.map {
            if (it.id == itemId) it.copy(counter = newCount) else it
        }
    }

    fun removeItem(itemId: Long) {
        _cartItems.value = _cartItems.value.filterNot { it.id == itemId }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}
