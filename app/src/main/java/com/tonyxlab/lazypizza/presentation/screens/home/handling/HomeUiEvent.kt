package com.tonyxlab.lazypizza.presentation.screens.home.handling

import com.tonyxlab.lazypizza.domain.model.Category
import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiEvent

sealed interface HomeUiEvent : UiEvent {
    data object PlaceCall : HomeUiEvent
    data object SignIn : HomeUiEvent
    data class SelectCategoryTab(val category: Category) : HomeUiEvent
    data class ClickOnPizza(val id: Long) : HomeUiEvent
    data class AddSideItemToCart(val addOnItem: AddOnItem) : HomeUiEvent
    data class ResetOrderStatus (val addOnItem: AddOnItem): HomeUiEvent
    data class IncrementQuantity(val addOnItem: AddOnItem)  : HomeUiEvent
    data class DecrementQuantity(val addOnItem: AddOnItem)  : HomeUiEvent
}