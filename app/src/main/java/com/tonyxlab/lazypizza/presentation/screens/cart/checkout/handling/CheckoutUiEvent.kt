package com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling

import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiEvent
import java.time.LocalDate
import java.time.LocalTime

sealed interface CheckoutUiEvent : UiEvent {

    data object GoBack : CheckoutUiEvent
    data class IncrementQuantity(val cartItem: CartItem) : CheckoutUiEvent
    data class DecrementQuantity(val cartItem: CartItem) : CheckoutUiEvent
    data class RemoveItem(val cartItem: CartItem) : CheckoutUiEvent
    data class SelectAddOnItem(val addOnItem: AddOnItem) : CheckoutUiEvent
    data object ExpandOrder : CheckoutUiEvent
    data object CollapseOrder : CheckoutUiEvent
    data object PlaceOrder : CheckoutUiEvent

    data class SelectPickupTime(val pickupTimeOption: PickupTimeOption) : CheckoutUiEvent

    data object OpenDatePicker: CheckoutUiEvent
    data object OpenTimePicker: CheckoutUiEvent

    data class SelectDate(val date: LocalDate): CheckoutUiEvent
    data class SelectTime(val time: LocalTime): CheckoutUiEvent
    data class PreviewTime(val time: LocalTime): CheckoutUiEvent


    data object DismissPicker: CheckoutUiEvent

}