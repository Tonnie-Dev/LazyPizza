package com.tonyxlab.lazypizza.presentation.screens.details.handling

import com.tonyxlab.lazypizza.domain.model.Topping
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiEvent

sealed interface DetailsUiEvent: UiEvent {

    data class ClickToppingCard(val topping: Topping): DetailsUiEvent
    data object SelectToppings: DetailsUiEvent
    data object AddExtraToppings: DetailsUiEvent
    data object RemoveExtraToppings: DetailsUiEvent
}