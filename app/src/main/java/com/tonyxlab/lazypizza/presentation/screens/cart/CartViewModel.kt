package com.tonyxlab.lazypizza.presentation.screens.cart

import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.cart.handling.CartActionEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.handling.CartUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.handling.CartUiState

typealias CartBaseViewModel = BaseViewModel<CartUiState, CartUiEvent, CartActionEvent>

class CartViewModel : CartBaseViewModel() {
    override val initialState: CartUiState
        get() = CartUiState()

    init {
        updateAggregateCartAmount()
    }

    override fun onEvent(event: CartUiEvent) {

        when (event) {
            is CartUiEvent.IncrementQuantity -> incrementCount(event.item)
            is CartUiEvent.DecrementQuantity -> decrementCount(event.item)
            is CartUiEvent.RemoveItem -> removeItem(event.item)
            CartUiEvent.Checkout -> {}
        }
    }

    private fun incrementCount(cartItem: CartItem) {

        val updatedList = currentState.cartItems.map {

            if (it.id == cartItem.id) {
                val newCount = it.counter.plus(1)
                        .coerceAtMost(5)
                it.copy(counter = newCount)
            } else it
        }

        updateState { it.copy(cartItems = updatedList) }
        updateAggregateCartAmount()
    }

    private fun decrementCount(cartItem: CartItem) {
        val updatedList = currentState.cartItems.map {

            if (it.id == cartItem.id) {
                val newCount = it.counter.minus(1)
                        .coerceAtLeast(1)
                it.copy(counter = newCount)
            } else it
        }

        updateState { it.copy(cartItems = updatedList) }
        updateAggregateCartAmount()
    }

    private fun removeItem(cartItem: CartItem) {

        val updatedList = currentState.cartItems.filterNot { it.id == cartItem.id }

        updateState { it.copy(cartItems = updatedList) }
        updateAggregateCartAmount()
    }

    private fun updateAggregateCartAmount() {

        val aggregateCartAmount = currentState.cartItems.sumOf { item -> item.unitPrice }
        updateState { it.copy(aggregateCartAmount = aggregateCartAmount) }
    }
    /* private fun incrementCount(item: CartItem) {

         val currentCount = currentState.cartItems.find { it.id == item.id }?.counter ?: 0
         val newCount = currentCount.plus(1)
                 .coerceAtMost(5)
         adjustCount(item = item, newCount = newCount)
     }

     private fun decrementCount(item: CartItem) {
         val currentCount = currentState.cartItems.find { it.id == item.id }?.counter ?: 0

         val newCount = currentCount.minus(1)
                 .coerceAtLeast(0)

         adjustCount(item = item, newCount = newCount)

     }

     private fun adjustCount(item: CartItem, newCount: Int) {

         val newSet = currentState.cartItems.toMutableSet()

         newSet.removeIf { it.id == item.id }

         if (newCount > 0) {
             newSet.add(item.copy(counter = newCount))
         }

         updateState { it.copy(cartItems = newSet.toList()) }
     }

     private fun removeItem(item: CartItem) {
         val newSet = currentState.cartItems.toMutableSet()
         newSet.removeIf { it.id == item.id }
     }*/
}
