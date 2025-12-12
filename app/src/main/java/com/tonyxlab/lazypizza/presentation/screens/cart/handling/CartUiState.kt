package com.tonyxlab.lazypizza.presentation.screens.cart.handling

import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState
import com.tonyxlab.lazypizza.utils.getMockAddOnItems

data class CartUiState(
    val cartItems: List<CartItem> = emptyList(),
    val suggestedAddOnItems: List<AddOnItem> = getMockAddOnItems().shuffled(),
    val aggregateCartAmount: Double = 0.0,
    val badgeCount: Int = 0
) : UiState
