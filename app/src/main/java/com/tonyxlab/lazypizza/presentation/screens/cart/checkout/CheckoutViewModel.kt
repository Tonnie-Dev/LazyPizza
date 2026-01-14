package com.tonyxlab.lazypizza.presentation.screens.cart.checkout

import androidx.lifecycle.viewModelScope
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.domain.extensions.calculateTotal
import com.tonyxlab.lazypizza.domain.extensions.extractRecommendedAddOnItems
import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.domain.model.toCartItem
import com.tonyxlab.lazypizza.domain.repository.CartRepository
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutActionEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.LocalTime

typealias CheckoutBaseViewModel =
        BaseViewModel<CheckoutUiState, CheckoutUiEvent, CheckoutActionEvent>

class CheckoutViewModel(
    private val repository: CartRepository
) : CheckoutBaseViewModel() {

    override val initialState: CheckoutUiState
        get() = CheckoutUiState()

    init {
        observeCart()
    }

    override fun onEvent(event: CheckoutUiEvent) {
        when (event) {
            CheckoutUiEvent.ExpandOrder -> expandOrderDetails()
            CheckoutUiEvent.CollapseOrder -> collapseOrderDetails()
            is CheckoutUiEvent.IncrementQuantity -> incrementQuantity(event.cartItem)
            is CheckoutUiEvent.DecrementQuantity -> decrementQuantity(event.cartItem)
            is CheckoutUiEvent.RemoveItem -> removeCartItem(event.cartItem)
            is CheckoutUiEvent.SelectAddOnItem -> {
                addItem(event.addOnItem)
            }

            CheckoutUiEvent.PlaceOrder -> {}
            is CheckoutUiEvent.SelectPickupTime -> {
                updateState {
                    it.copy(
                            dateTimePickerState = currentState.dateTimePickerState.copy(
                                    pickupTimeOption = event.pickupTimeOption
                            )
                    )
                }
            }

            CheckoutUiEvent.OpenDatePicker -> openDatePicker()
            CheckoutUiEvent.OpenTimePicker -> openTimePicker()
            is CheckoutUiEvent.SelectDate -> selectDate(event.date)
            is CheckoutUiEvent.SelectTime -> selectTime(event.time)
            CheckoutUiEvent.DismissPicker -> dismissPicker()
            is CheckoutUiEvent.PreviewTime -> previewTime(event.time)

            CheckoutUiEvent.GoBack -> {
                sendActionEvent(CheckoutActionEvent.NavigateBack)
            }
        }
    }

    private fun observeCart() {
        repository.cartItems.onEach { cartItems ->
            with(cartItems) {
                updateState {
                    it.copy(
                            cartItems = this,
                            totalAmount = calculateTotal(),
                            suggestedAddOnItems = extractRecommendedAddOnItems()
                    )
                }
            }
        }
                .launchIn(viewModelScope)
    }

    private fun expandOrderDetails() {
        updateState { it.copy(expanded = true) }
    }

    private fun collapseOrderDetails() {
        updateState { it.copy(expanded = false) }
    }

    private fun incrementQuantity(cartItem: CartItem) {
        launch {

            repository.updateCount(
                    cartItem = cartItem,
                    newCount = cartItem.counter + 1
            )
        }
    }

    private fun decrementQuantity(cartItem: CartItem) {
        launch {
            repository.updateCount(
                    cartItem = cartItem,
                    newCount = (cartItem.counter - 1)
                            .coerceAtLeast(minimumValue = 1)
            )
        }
    }

    private fun addItem(addOnItem: AddOnItem) {
        launch {
            repository.addItem(cartItem = addOnItem.toCartItem())
        }
    }

    private fun removeCartItem(cartItem: CartItem) {
        launch {
            repository.removeItem(cartItem)
        }
    }

    private fun openDatePicker() {

        updateState {
            it.copy(
                    dateTimePickerState = currentState.dateTimePickerState.copy(
                            showDatePicker = true,
                            showTimePicker = false
                    )
            )
        }
    }

    private fun selectDate(date: LocalDate) {
        updateState {
            it.copy(
                    dateTimePickerState = currentState.dateTimePickerState.copy(
                            selectedDate = date,
                            showTimePicker = true
                    )
            )
        }
    }

    private fun openTimePicker() {
        updateState {
            it.copy(
                    dateTimePickerState = currentState.dateTimePickerState.copy(
                            showDatePicker = false,
                            showTimePicker = true
                    )
            )
        }
    }

    private fun previewTime(time: LocalTime) {

        val date = currentState.dateTimePickerState.selectedDate ?: return
        val error = validatePickupTime(date = date, time = time)

        updateState {
            it.copy(
                    dateTimePickerState = currentState.dateTimePickerState.copy(
                            selectedTime = time,
                            pickTimeError = error
                    )
            )
        }
    }

    private fun selectTime(time: LocalTime) {
        val date = currentState.dateTimePickerState.selectedDate ?: return
        val error = validatePickupTime(date = date, time = time)
        updateState {
            it.copy(
                    dateTimePickerState = currentState.dateTimePickerState.copy(
                            selectedTime = time,
                            showTimePicker = false,
                            pickTimeError = error
                    )
            )
        }

    }

    private fun dismissPicker() {

        updateState {
            it.copy(
                    dateTimePickerState = currentState.dateTimePickerState.copy(
                            showDatePicker = false,
                            showTimePicker = false
                    )
            )
        }
    }

    private fun validatePickupTime(date: LocalDate, time: LocalTime): Int? {

        val earliestPickupTime = LocalTime.of(10, 15)
        val latestPickupTime = LocalTime.of(21, 45)

        if (time.isBefore(earliestPickupTime) || time.isAfter(latestPickupTime)) {
            return R.string.cap_text_pickup_available_time
        }

        if (date == LocalDate.now()) {
            val adjustedPickupTime = LocalTime.now()
                    .plusMinutes(15)
            if (time.isBefore(adjustedPickupTime)) {

                return R.string.cap_text_pickup_possible_time
            }
        }

        return null
    }
}