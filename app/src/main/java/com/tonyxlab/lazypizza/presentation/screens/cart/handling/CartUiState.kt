package com.tonyxlab.lazypizza.presentation.screens.cart.handling

import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState
import com.tonyxlab.lazypizza.utils.cartItemsMock

data class CartUiState(val cartItems: List<CartItem> = cartItemsMock): UiState
