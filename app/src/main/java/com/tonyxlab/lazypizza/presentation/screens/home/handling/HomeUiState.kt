package com.tonyxlab.lazypizza.presentation.screens.home.handling

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.domain.model.Category
import com.tonyxlab.lazypizza.domain.model.Pizza
import com.tonyxlab.lazypizza.domain.model.SearchItem
import com.tonyxlab.lazypizza.domain.model.SideItem
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState
import com.tonyxlab.lazypizza.utils.getMockSideItems
import com.tonyxlab.lazypizza.utils.mockPizzas

@Stable
data class HomeUiState(
    val phoneNumber: String = "+1 (555) 321-7890",
    val textFieldState: TextFieldState = TextFieldState(),
    val isTextEmpty: Boolean = false,
    val allPizzaItems: List<Pizza> = mockPizzas,
    val allSideItems: List<SideItem> = getMockSideItems(),
    val filteredSideItems: List<SideItem> = emptyList(),
    val cartItems: List<CartItem> = emptyList(),
    val searchResults: List<SearchItem> = emptyList(),
    val selectedCategory: Category = Category.PIZZA,
    val aggregateItemTotal: Double = 0.0,
    val badgeCount: Int = 0
) : UiState