package com.tonyxlab.lazypizza.presentation.screens.cart.cart.handling

import com.tonyxlab.lazypizza.presentation.core.base.handling.ActionEvent

sealed interface CartActionEvent: ActionEvent{

    object NavigateBackToMenu: CartActionEvent
    object NavigateToCheckout: CartActionEvent
}