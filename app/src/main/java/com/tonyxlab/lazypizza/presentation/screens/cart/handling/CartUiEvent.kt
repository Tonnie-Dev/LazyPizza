package com.tonyxlab.lazypizza.presentation.screens.cart.handling

import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiEvent

sealed interface CartUiEvent : UiEvent {
    data class RemoveItem(val item: CartItem) : CartUiEvent
    data class IncrementQuantity(val item: CartItem) : CartUiEvent
    data class DecrementQuantity(val item: CartItem) : CartUiEvent
    data object Checkout : CartUiEvent
}