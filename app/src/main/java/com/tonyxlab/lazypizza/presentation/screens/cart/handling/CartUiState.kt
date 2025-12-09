package com.tonyxlab.lazypizza.presentation.screens.cart.handling

import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.domain.model.SideItem
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState
import com.tonyxlab.lazypizza.utils.cartItemsMock
import com.tonyxlab.lazypizza.utils.getMockAddOnItems

data class CartUiState(
  //  val cartItemsList: List<CartItem> = cartItemsMock,
    val addOnItemsList: List<SideItem> = getMockAddOnItems().shuffled(),
    val pickedAddOnItemsList: List<SideItem> = emptyList(),
   // val aggregateCartAmount: Double = 1.0
) : UiState
