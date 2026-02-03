package com.tonyxlab.lazypizza.presentation.screens.menu.details.handling

import androidx.compose.runtime.Stable
import com.tonyxlab.lazypizza.domain.model.Pizza
import com.tonyxlab.lazypizza.domain.model.Topping
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState
import com.tonyxlab.lazypizza.utils.toppingsMock

@Stable
data class DetailsUiState(
    val pizzaStateItem: Pizza? = null,
    val aggregatePrice: Double = 0.0,
    val toppings: List<Topping> = toppingsMock,
    val selectedToppings: Set<Topping> = emptySet()
) : UiState