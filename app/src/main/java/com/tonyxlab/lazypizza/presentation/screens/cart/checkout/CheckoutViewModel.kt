package com.tonyxlab.lazypizza.presentation.screens.cart.checkout

import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutActionEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiState

typealias CheckoutBaseViewModel =
        BaseViewModel<CheckoutUiState, CheckoutUiEvent, CheckoutActionEvent>

class CheckoutViewModel : CheckoutBaseViewModel(){

    override val initialState: CheckoutUiState
        get() = TODO("Not yet implemented")

    override fun onEvent(event: CheckoutUiEvent) {
        TODO("Not yet implemented")
    }
}