package com.tonyxlab.lazypizza.presentation.screens.details.handling

import com.tonyxlab.lazypizza.domain.model.Topping
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiEvent

sealed interface DetailsUiEvent : UiEvent {

    data class ClickToppingCard(val topping: Topping) : DetailsUiEvent
    data class AddExtraToppings(val topping: Topping) : DetailsUiEvent
    data object AddToCart: DetailsUiEvent
    data class RemoveExtraToppings(val topping: Topping) : DetailsUiEvent
}