package com.tonyxlab.lazypizza.presentation.screens.home.handling

import com.tonyxlab.lazypizza.domain.model.Category
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiEvent

sealed interface HomeUiEvent : UiEvent {
    data object PlaceCall : HomeUiEvent
    data class SelectCategoryTab(val category: Category) : HomeUiEvent
    data class ClickOnPizza(val id: Long) : HomeUiEvent
    data class ClickOnSideItem(val id: Long) : HomeUiEvent
    data object AddToCart : HomeUiEvent
    data object ResetOrder : HomeUiEvent
    data object IncrementQuantity : HomeUiEvent
    data object DecrementQuantity : HomeUiEvent
}