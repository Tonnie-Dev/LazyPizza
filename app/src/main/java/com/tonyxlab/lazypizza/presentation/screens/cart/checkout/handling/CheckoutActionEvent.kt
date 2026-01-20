package com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling

import com.tonyxlab.lazypizza.presentation.core.base.handling.ActionEvent

sealed interface CheckoutActionEvent: ActionEvent {

    data object NavigateBack: CheckoutActionEvent
    data object ExitCheckout: CheckoutActionEvent
}