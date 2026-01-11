package com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling

import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiEvent

sealed interface CheckoutUiEvent : UiEvent {
    data object GoBack : CheckoutUiEvent
    data class SelectPickupTime(val scheduled: Boolean) : CheckoutUiEvent
    data class SelectAddOnItem(val addOnItem: AddOnItem) : CheckoutUiEvent
    data object ExpandOrder : CheckoutUiEvent
    data object CollapseOrder : CheckoutUiEvent
    data object PlaceOrder : CheckoutUiEvent
}