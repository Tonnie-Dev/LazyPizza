package com.tonyxlab.lazypizza.presentation.screens.cart.cart.handling

import com.tonyxlab.lazypizza.presentation.core.base.handling.ActionEvent

sealed interface CartActionEvent: ActionEvent{
    data object NavigateBackToMenu: CartActionEvent
    data object NavigateToCheckout: CartActionEvent
    data object NavigateToAuth: CartActionEvent
}