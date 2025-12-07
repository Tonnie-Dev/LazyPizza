package com.tonyxlab.lazypizza.presentation.screens.cart

import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.domain.model.SideItem
import com.tonyxlab.lazypizza.domain.model.toCartItem
import com.tonyxlab.lazypizza.domain.model.toSideItem
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.cart.handling.CartActionEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.handling.CartUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.handling.CartUiState

typealias CartBaseViewModel = BaseViewModel<CartUiState, CartUiEvent, CartActionEvent>

class CartViewModel : CartBaseViewModel() {
    override val initialState: CartUiState
        get() = CartUiState()

    init {

        val initialAggregate = currentState.cartItemsList.sumOf { it.unitPrice * it.counter }
        updateState { it.copy(aggregateCartAmount = initialAggregate) }
    }

    override fun onEvent(event: CartUiEvent) {

        when (event) {
            is CartUiEvent.IncrementQuantity -> incrementCount(event.item)
            is CartUiEvent.DecrementQuantity -> decrementCount(event.item)
            is CartUiEvent.RemoveItem -> removeFromCart(event.item)
            is CartUiEvent.PickAddOn -> pickAddOn(event.sideItem)
            CartUiEvent.BackToMenu -> navigateToMenu()
            CartUiEvent.Checkout -> {}
        }
    }

    private fun incrementCount(cartItem: CartItem) {

        val updatedList = currentState.cartItemsList.map {

            if (it.id == cartItem.id) {
                val newCount = it.counter.plus(1)
                        .coerceAtMost(5)
                it.copy(counter = newCount)
            } else it
        }
        val newAggregate = updatedList.sumOf { it.unitPrice * it.counter }
        updateState { it.copy(cartItemsList = updatedList, aggregateCartAmount = newAggregate) }

    }

    private fun decrementCount(cartItem: CartItem) {
        val updatedList = currentState.cartItemsList.map {

            if (it.id == cartItem.id) {
                val newCount = it.counter.minus(1)
                        .coerceAtLeast(1)
                it.copy(counter = newCount)
            } else it
        }

        val newAggregate = updatedList.sumOf { it.unitPrice * it.counter }
        updateState { it.copy(cartItemsList = updatedList, aggregateCartAmount = newAggregate) }

    }

    fun pickAddOn(sideItem: SideItem) {
        val pickedItem = sideItem.toCartItem()

        val updatedPickedAddOnsList =
            currentState.pickedAddOnItemsList
                    .plus(sideItem)
                    .distinctBy { it.id }

        val updatedCartItemsList =
            currentState.cartItemsList
                    .plus(pickedItem)
                    .distinctBy { it.id }

        val updatedAddOnItems =
            currentState.addOnItemsList
                    .filterNot { it.id == sideItem.id }

        val newAggregate = updatedCartItemsList
                .sumOf { it.unitPrice * it.counter }

        updateState {
            it.copy(
                    cartItemsList = updatedCartItemsList,
                    addOnItemsList = updatedAddOnItems,
                    pickedAddOnItemsList = updatedPickedAddOnsList,
                    aggregateCartAmount = newAggregate
            )
        }
    }

    private fun removeFromCart(cartItem: CartItem) {

        val updatedPickedItemsList =
            currentState.pickedAddOnItemsList.filterNot { it.id == cartItem.id }

        val updatedCartList = currentState
                .cartItemsList.filterNot { it.id == cartItem.id }

        val newAggregate = updatedCartList
                .sumOf { it.unitPrice * it.counter }

        val wasPickedAddOn = currentState.pickedAddOnItemsList
                .any { it.id == cartItem.id }

        val updatedAddOnOns = if (wasPickedAddOn)
            currentState.addOnItemsList
                    .plus(cartItem.toSideItem())
                    .shuffled()
        else
            currentState.addOnItemsList

        updateState {
            it.copy(
                    cartItemsList = updatedCartList,
                    addOnItemsList = updatedAddOnOns,
                    pickedAddOnItemsList = updatedPickedItemsList,
                    aggregateCartAmount = newAggregate,
            )
        }
    }

    private fun navigateToMenu() {
        sendActionEvent(actionEvent = CartActionEvent.NavigateBackToMenu)
    }
}
