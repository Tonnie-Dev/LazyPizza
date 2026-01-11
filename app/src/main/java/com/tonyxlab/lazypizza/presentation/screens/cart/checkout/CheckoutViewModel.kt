package com.tonyxlab.lazypizza.presentation.screens.cart.checkout

import androidx.lifecycle.viewModelScope
import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.domain.repository.CartRepository
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutActionEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

typealias CheckoutBaseViewModel =
        BaseViewModel<CheckoutUiState, CheckoutUiEvent, CheckoutActionEvent>

class CheckoutViewModel(
    private val repository: CartRepository
) : CheckoutBaseViewModel() {

    override val initialState: CheckoutUiState
        get() = CheckoutUiState()

    init {
        repository.cartItems.onEach { cartItems ->

            updateState { it.copy(cartItems = cartItems) }
        }.launchIn(viewModelScope)
    }

    override fun onEvent(event: CheckoutUiEvent) {

        when (event) {
            CheckoutUiEvent.ExpandOrder -> expandOrderDetails()
            CheckoutUiEvent.CollapseOrder -> collapseOrderDetails()
            is CheckoutUiEvent.IncrementQuantity -> incrementQuantity(event.cartItem)
            is CheckoutUiEvent.DecrementQuantity -> decrementQuantity(event.cartItem)
            is CheckoutUiEvent.RemoveItem -> removeCartItem(event.cartItem)
            is CheckoutUiEvent.SelectAddOnItem -> {}
            CheckoutUiEvent.PlaceOrder -> {}
            is CheckoutUiEvent.SelectPickupTime -> {
                updateState { it.copy(pickupTimeOption = event.pickupTimeOption) }
            }
            CheckoutUiEvent.GoBack -> {
                sendActionEvent(CheckoutActionEvent.NavigateBack)
            }
        }
    }

    private fun expandOrderDetails() {
        updateState { it.copy(expanded = true) }
    }

    private fun collapseOrderDetails() {
        updateState { it.copy(expanded = false) }
    }

    private fun incrementQuantity(cartItem: CartItem) {
        launch {

            repository.updateCount(
                    cartItem = cartItem,
                    newCount = cartItem.counter + 1
            )
        }
    }

    private fun decrementQuantity(cartItem: CartItem) {
        launch {
            repository.updateCount(
                    cartItem = cartItem,
                    newCount = (cartItem.counter - 1)
                            .coerceAtLeast(minimumValue = 1)
            )
        }
    }

    private fun removeCartItem(cartItem: CartItem) {
        launch {
            repository.removeItem(cartItem)
        }
    }
}