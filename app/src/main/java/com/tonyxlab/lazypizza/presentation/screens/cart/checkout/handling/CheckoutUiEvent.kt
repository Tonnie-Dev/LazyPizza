package com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling

import com.tonyxlab.lazypizza.presentation.core.base.handling.UiEvent

sealed interface CheckoutUiEvent: UiEvent{

    object GoBack: CheckoutUiEvent
    data class SelectPickupTime(val scheduled: Boolean): CheckoutUiEvent
}