package com.tonyxlab.lazypizza.presentation.screens.cart

import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.cart.handling.CartActionEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.handling.CartUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.handling.CartUiState

typealias CartBaseViewModel = BaseViewModel<CartUiState, CartUiEvent, CartActionEvent>
class CartViewModel: CartBaseViewModel(){
    override val initialState: CartUiState
        get() = CartUiState()

    override fun onEvent(event: CartUiEvent) {
        TODO("Not yet implemented")
    }
}