package com.tonyxlab.lazypizza.data.repository

import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.domain.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartRepositoryImpl : CartRepository {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    override val cartItems = _cartItems.asStateFlow()

    override fun addItem(cartItem: CartItem) {
        // convert mutable state flow to mutable list

        val currentCartItemsList = _cartItems.value.toMutableList()
        // check if the item to be added is contained in the existing cart list

        val itemIndex = _cartItems.value.indexOfFirst { it.id == cartItem.id }

        if (itemIndex >= 0) {
            //item exists - for updating
            val updatedItem = _cartItems.value[itemIndex].copy(counter = cartItem.counter)
            currentCartItemsList[itemIndex] = updatedItem
        } else {
            // we add item as a new item
            currentCartItemsList.add(cartItem)
        }
        _cartItems.update { currentCartItemsList }
    }

    override fun removeItem(itemId: Long) {

        val updatedList = cartItems.value.filterNot { it.id == itemId }
        _cartItems.update { updatedList }
    }

    override fun updateCount(
        cartItem: CartItem,
        newCount: Int
    ) {
        _cartItems.update { list ->
            list.mapNotNull { item ->

                when {
                    item.id == cartItem.id && cartItem.counter <= 0 -> null
                    item.id == cartItem.id -> item.copy(counter = newCount)
                    else -> item
                }

            }
        }
    }

    override fun clear() {
        _cartItems.update { emptyList() }
    }
}
