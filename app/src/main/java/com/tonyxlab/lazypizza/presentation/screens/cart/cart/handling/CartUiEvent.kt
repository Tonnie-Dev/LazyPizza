package com.tonyxlab.lazypizza.presentation.screens.cart.cart.handling

import com.tonyxlab.lazypizza.domain.model.MenuItem
import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiEvent

sealed interface CartUiEvent : UiEvent {
    data class RemoveItem(val item: MenuItem) : CartUiEvent
    data class IncrementQuantity(val item: MenuItem) : CartUiEvent
    data class DecrementQuantity(val item: MenuItem) : CartUiEvent
    data object BackToMenu : CartUiEvent
    data object Checkout : CartUiEvent
    data class SelectAddOn(val addOnItem: AddOnItem) : CartUiEvent
}