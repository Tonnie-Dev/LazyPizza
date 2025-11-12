package com.tonyxlab.lazypizza.presentation.screens.home.handling

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import com.tonyxlab.lazypizza.domain.model.Pizza
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState
import com.tonyxlab.lazypizza.utils.mockPizzas

@Stable
data class HomeUiState(
    val phoneNumber: String = "+1 (555) 321-7890",
    val textFieldState: TextFieldState = TextFieldState(),
    val isTextEmpty: Boolean = false,
    val allPizzaItems: List<Pizza> = mockPizzas, //emptyList(),
    val filteredPizzaItems: List<Pizza> = emptyList()
) : UiState {}