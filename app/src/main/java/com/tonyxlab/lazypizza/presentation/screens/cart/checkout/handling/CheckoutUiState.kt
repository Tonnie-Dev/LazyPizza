@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.text.input.TextFieldState
import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.domain.model.MenuItem
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState
import com.tonyxlab.lazypizza.utils.getMockAddOnItems
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class CheckoutUiState(
    val menuItems: List<MenuItem> = emptyList(),
    val suggestedAddOnItems: List<AddOnItem> = getMockAddOnItems().shuffled(),
    val textFieldState: TextFieldState = TextFieldState(),
    val expanded: Boolean = false,
    val totalAmount: Double = 0.0,
    val dateTimePickerState: DateTimePickerState = DateTimePickerState(),
    val checkoutStep: CheckoutStep = CheckoutStep.STEP_CHECKOUT,
    val orderNumber: String = ""

) : UiState {

    data class DateTimePickerState(
        val pickupTimeOption: PickupTimeOption = PickupTimeOption.EARLIEST,

        val earliestPickupTime: LocalDateTime = LocalDateTime.now(),

        val showDatePicker: Boolean = false,
        val showTimePicker: Boolean = false,

        val selectedDate: LocalDate = LocalDate.now(),
        val selectedTime: LocalTime = LocalTime.now(),

        val scheduledTimeConfirmed: Boolean = false,

        @DrawableRes
        val pickTimeError: Int? = null
    )
}

enum class PickupTimeOption { EARLIEST, SCHEDULED }

enum class CheckoutStep { STEP_CHECKOUT, STEP_CONFIRMATION }