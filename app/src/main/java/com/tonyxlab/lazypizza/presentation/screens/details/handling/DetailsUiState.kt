package com.tonyxlab.lazypizza.presentation.screens.details.handling

import androidx.compose.runtime.Stable
import com.tonyxlab.lazypizza.domain.model.Topping
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState
import com.tonyxlab.lazypizza.utils.mockToppings

@Stable
data class DetailsUiState(
    val isCodingSweet: Boolean = true,
    val pizzaItem: PizzaItem = PizzaItem(),
    val toppings: List<Topping> = mockToppings,
    val selectedToppings: List<Topping> = listOf()
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