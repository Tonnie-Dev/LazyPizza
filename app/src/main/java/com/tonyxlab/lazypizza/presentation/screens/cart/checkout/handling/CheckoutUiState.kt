package com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling

import androidx.compose.foundation.text.input.TextFieldState
import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState
import com.tonyxlab.lazypizza.utils.getMockAddOnItems

data class CheckoutUiState(
    val cartItems: List<CartItem> = emptyList(),
    val suggestedAddOnItems: List<AddOnItem> = getMockAddOnItems().shuffled(),
    val textFieldState: TextFieldState = TextFieldState(),
    val pickupTimeOption: PickupTimeOption = PickupTimeOption.EARLIEST,
    val expanded: Boolean = false,

) : UiState {

}

enum class PickupTimeOption { EARLIEST, SCHEDULED }