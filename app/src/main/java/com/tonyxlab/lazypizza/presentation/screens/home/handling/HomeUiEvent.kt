package com.tonyxlab.lazypizza.presentation.screens.home.handling

import com.tonyxlab.lazypizza.domain.model.Category
import com.tonyxlab.lazypizza.domain.model.SideItem
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiEvent

sealed interface HomeUiEvent : UiEvent {
    data object PlaceCall : HomeUiEvent
    data class SelectCategoryTab(val category: Category) : HomeUiEvent
    data class ClickOnPizza(val id: Long) : HomeUiEvent
    data class AddSideItemToCart(val sideItem: SideItem) : HomeUiEvent
    data class ResetOrderStatus (val sideItem: SideItem): HomeUiEvent
    data class IncrementQuantity(val sideItem: SideItem)  : HomeUiEvent
    data class DecrementQuantity(val sideItem: SideItem)  : HomeUiEvent
}