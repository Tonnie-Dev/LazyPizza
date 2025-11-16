package com.tonyxlab.lazypizza.presentation.screens.details.handling

import com.tonyxlab.lazypizza.presentation.core.base.handling.UiEvent

sealed interface DetailsUiEvent: UiEvent {

    data object ClickToppingCard: DetailsUiEvent
    data object SelectToppings: DetailsUiEvent
    data object AddExtraToppings: DetailsUiEvent
    data object RemoveExtraToppings: DetailsUiEvent
}