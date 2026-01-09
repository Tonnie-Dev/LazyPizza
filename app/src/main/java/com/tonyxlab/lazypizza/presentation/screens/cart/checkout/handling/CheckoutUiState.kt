package com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling

import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState

data class CheckoutUiState(
    val cartItems: List<CartItem> = emptyList(),
    val pickupTimeOption: PickupTimeOption = PickupTimeOption.EARLIEST,
    val expanded: Boolean = false
) : UiState {

}

enum class PickupTimeOption { EARLIEST, SCHEDULED }