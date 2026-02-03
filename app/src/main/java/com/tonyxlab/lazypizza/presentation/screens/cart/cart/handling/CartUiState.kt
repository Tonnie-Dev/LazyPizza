package com.tonyxlab.lazypizza.presentation.screens.cart.cart.handling

import com.tonyxlab.lazypizza.domain.model.MenuItem
import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState
import com.tonyxlab.lazypizza.utils.getMockAddOnItems

data class CartUiState(
    val menuItems: List<MenuItem> = emptyList(),
    val suggestedAddOnItems: List<AddOnItem> =emptyList(),
    val aggregateCartAmount: Double = 0.0,
    val badgeCount: Int = 0
) : UiState
