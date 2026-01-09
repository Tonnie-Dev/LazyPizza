package com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling

import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiEvent

sealed interface CheckoutUiEvent : UiEvent {

    data object GoBack : CheckoutUiEvent
    data class SelectPickupTime(val scheduled: Boolean) : CheckoutUiEvent
    data object ExpandOrder : CheckoutUiEvent
    data object CollapseOrder : CheckoutUiEvent

    // Similar Events
    data class RemoveItem(val item: CartItem) : CheckoutUiEvent
    data class IncrementQuantity(val item: CartItem) : CheckoutUiEvent
    data class DecrementQuantity(val item: CartItem) : CheckoutUiEvent
}