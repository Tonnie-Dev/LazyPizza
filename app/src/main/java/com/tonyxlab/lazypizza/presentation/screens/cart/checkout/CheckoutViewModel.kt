@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.lazypizza.presentation.screens.cart.checkout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.snapshotFlow
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
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.PickupTimeOption
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.LocalDateTime
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
        updateEarliestPickupTime()
    }

    override fun onEvent(event: CheckoutUiEvent) {
        when (event) {

            CheckoutUiEvent.ExpandOrder -> expandOrderDetails()
            CheckoutUiEvent.CollapseOrder -> collapseOrderDetails()

            is CheckoutUiEvent.IncrementQuantity -> incrementQuantity(event.cartItem)
            is CheckoutUiEvent.DecrementQuantity -> decrementQuantity(event.cartItem)
            is CheckoutUiEvent.RemoveItem -> removeCartItem(event.cartItem)

            CheckoutUiEvent.OpenDatePicker -> openDatePicker()
            CheckoutUiEvent.OpenTimePicker -> openTimePicker()

            is CheckoutUiEvent.SelectPickupOption -> selectPickupOption(event.pickupTimeOption)
            is CheckoutUiEvent.SelectDate -> selectDate(event.date)
            is CheckoutUiEvent.PreviewTime -> previewTime(event.time)
            is CheckoutUiEvent.SelectTime -> selectTime(event.time)

            CheckoutUiEvent.DismissPicker -> dismissPicker()
            is CheckoutUiEvent.SelectAddOnItem -> {
                addItem(event.addOnItem)
            }

            CheckoutUiEvent.PlaceOrder -> {}

            CheckoutUiEvent.GoBack -> {
                sendActionEvent(CheckoutActionEvent.NavigateBack)
            }

            CheckoutUiEvent.ExitCheckout -> {}
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

    private fun selectPickupOption(pickupTimeOption: PickupTimeOption) {
        updateState {
            it.copy(
                    dateTimePickerState = currentState.dateTimePickerState.copy(
                            pickupTimeOption = pickupTimeOption
                    )
            )
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
                            scheduledTimeConfirmed = true,
                            pickTimeError = error
                    )
            )
        }
    }

    private fun dismissPicker() {

        val pickerState = currentState.dateTimePickerState

        val fallbackToEarliest =
            pickerState.pickupTimeOption == PickupTimeOption.SCHEDULED &&
                    !pickerState.scheduledTimeConfirmed

        updateState {
            it.copy(
                    dateTimePickerState = if (fallbackToEarliest) {

                        pickerState.copy(
                                pickupTimeOption = PickupTimeOption.EARLIEST,
                                selectedDate = LocalDate.now(),
                                selectedTime = LocalTime.of(
                                        pickerState.earliestPickupTime.hour,
                                        pickerState.earliestPickupTime.minute
                                ),
                                showDatePicker = false,
                                showTimePicker = false
                        )
                    } else {
                        pickerState.copy(

                                showDatePicker = false,
                                showTimePicker = false
                        )
                    }
            )
        }
    }

    private fun calculateEarliestPickupTime(
        timeNow: LocalTime = LocalTime.now(),
        dateNow: LocalDate = LocalDate.now()
    ): LocalDateTime {

        val pickupStart = LocalTime.of(10, 15)
        val pickupEnd = LocalTime.of(21, 45)
        val prepMinutes = 15L

        val targetTime = timeNow.plusMinutes(prepMinutes)

        return when {

            targetTime.isBefore(pickupStart) ->
                LocalDateTime.of(dateNow, pickupStart)

            !targetTime.isAfter(pickupEnd) ->
                LocalDateTime.of(dateNow, targetTime)

            else ->
                LocalDateTime.of(dateNow.plusDays(1), pickupStart)

        }
    }

    private fun updateEarliestPickupTime() {
        launch {

            while (true) {
                updateState {
                    it.copy(
                            dateTimePickerState = currentState.dateTimePickerState.copy(
                                    earliestPickupTime = calculateEarliestPickupTime()
                            )
                    )
                }
                delay(30_000)
            }
        }
    }

    private fun validatePickupTime(date: LocalDate, time: LocalTime): Int? {

        if (date == LocalDate.now()) {
            val adjustedPickupTime = LocalTime.now()
                    .plusMinutes(15)
            if (time.isBefore(adjustedPickupTime)) {

                return R.string.cap_text_pickup_possible_time
            }
        }
        val earliestPickupTime = LocalTime.of(10, 15)
        val latestPickupTime = LocalTime.of(21, 45)

        if (time.isBefore(earliestPickupTime) || time.isAfter(latestPickupTime)) {
            return R.string.cap_text_pickup_available_time
        }
        return null
    }
}