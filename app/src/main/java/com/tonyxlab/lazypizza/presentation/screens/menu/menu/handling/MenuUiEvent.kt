package com.tonyxlab.lazypizza.presentation.screens.menu.menu.handling

import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.domain.model.Category
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiEvent

sealed interface MenuUiEvent : UiEvent {
    data object PlaceCall : MenuUiEvent
    data object SignIn : MenuUiEvent
    data object ShowLogoutDialog : MenuUiEvent
    data object ConfirmLogoutDialog : MenuUiEvent
    data object DismissLogoutDialog : MenuUiEvent
    data class SelectCategoryTab(val category: Category) : MenuUiEvent
    data class ClickOnPizza(val id: Long) : MenuUiEvent
    data class AddSideItemToCart(val addOnItem: AddOnItem) : MenuUiEvent
    data class ResetOrderStatus(val addOnItem: AddOnItem) : MenuUiEvent
    data class IncrementQuantity(val addOnItem: AddOnItem) : MenuUiEvent
    data class DecrementQuantity(val addOnItem: AddOnItem) : MenuUiEvent
    data object SeedFirestore : MenuUiEvent
    data object ClearFirestore : MenuUiEvent
}