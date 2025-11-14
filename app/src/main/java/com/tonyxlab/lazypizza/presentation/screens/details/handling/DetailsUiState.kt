package com.tonyxlab.lazypizza.presentation.screens.details.handling

import androidx.compose.runtime.Stable
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState

@Stable
data class DetailsUiState(
    val isCodingSweet: Boolean = true,
    val pizzaItem : PizzaItem= PizzaItem()
) : UiState {
    @Stable
    data class PizzaItem(
        val name: String = "Margherita",
        val imageUrl: String = "",
        val ingredients: List<String> = listOf("Tomato sauce", "Mozzarella", "Fresh basil", "Olive oil")
    )
}