package com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling

import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState

data class CheckoutUiState(val pickupTimeOption: PickupTimeOption = PickupTimeOption.EARLIEST): UiState{



}
enum class PickupTimeOption { EARLIEST, SCHEDULED }