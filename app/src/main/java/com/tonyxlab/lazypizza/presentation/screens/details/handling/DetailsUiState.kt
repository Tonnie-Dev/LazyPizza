package com.tonyxlab.lazypizza.presentation.screens.details.handling

import androidx.compose.runtime.Stable
import com.tonyxlab.lazypizza.domain.model.Pizza
import com.tonyxlab.lazypizza.domain.model.Topping
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState
import com.tonyxlab.lazypizza.utils.mockToppings

@Stable
data class DetailsUiState(
    val pizzaStateItem: Pizza? = null,
    val toppings: List<Topping> = mockToppings,
    val selectedToppings: Set<Topping> = emptySet()
) : UiState {
    @Stable
    data class PizzaItem(
        val name: String = "Margherita",
        val imageUrl: String = "",
        val ingredients: List<String> = listOf(
                "Tomato sauce",
                "Mozzarella",
                "Fresh basil",
                "Olive oil"
        )
    )
}