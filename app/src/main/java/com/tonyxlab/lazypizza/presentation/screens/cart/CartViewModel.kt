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

        val initialAggregate = currentState.cartItems.sumOf { it.unitPrice * it.counter }
        updateState { it.copy(aggregateCartAmount = initialAggregate) }
    }

    override fun onEvent(event: CartUiEvent) {

        when (event) {
            is CartUiEvent.IncrementQuantity -> incrementCount(event.item)
            is CartUiEvent.DecrementQuantity -> decrementCount(event.item)
            is CartUiEvent.RemoveItem -> removeItem(event.item)

            CartUiEvent.BackToMenu -> navigateToMenu()
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
        val newAggregate = updatedList.sumOf { it.unitPrice * it.counter }
        updateState { it.copy(cartItems = updatedList, aggregateCartAmount = newAggregate) }

    }

    private fun decrementCount(cartItem: CartItem) {
        val updatedList = currentState.cartItems.map {

            if (it.id == cartItem.id) {
                val newCount = it.counter.minus(1)
                        .coerceAtLeast(1)
                it.copy(counter = newCount)
            } else it
        }

        val newAggregate = updatedList.sumOf { it.unitPrice * it.counter }
        updateState { it.copy(cartItems = updatedList, aggregateCartAmount = newAggregate) }

    }

    private fun removeItem(cartItem: CartItem) {
        val updatedList = currentState.cartItems.filterNot { it.id == cartItem.id }
        val newAggregate = updatedList.sumOf { it.unitPrice * it.counter }
        updateState { it.copy(cartItems = updatedList, aggregateCartAmount = newAggregate) }
    }

    private fun navigateToMenu(){
        sendActionEvent(actionEvent = CartActionEvent.NavigateBackToMenu)
    }

}
