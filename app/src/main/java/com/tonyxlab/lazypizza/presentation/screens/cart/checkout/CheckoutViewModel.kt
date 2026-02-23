@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.lazypizza.presentation.screens.cart.checkout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.domain.extensions.calculateTotal
import com.tonyxlab.lazypizza.domain.firebase.AuthState
import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.domain.model.MenuItem
import com.tonyxlab.lazypizza.domain.model.Order
import com.tonyxlab.lazypizza.domain.model.OrderStatus
import com.tonyxlab.lazypizza.domain.model.toMenuItem
import com.tonyxlab.lazypizza.domain.repository.AuthRepository
import com.tonyxlab.lazypizza.domain.repository.CartRepository
import com.tonyxlab.lazypizza.domain.repository.CatalogRepository
import com.tonyxlab.lazypizza.domain.repository.OrderRepository
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutActionEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutStep
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiState
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.PickupTimeOption
import com.tonyxlab.lazypizza.utils.generateOrderNumber
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

typealias CheckoutBaseViewModel =
        BaseViewModel<CheckoutUiState, CheckoutUiEvent, CheckoutActionEvent>

class CheckoutViewModel(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val authRepository: AuthRepository,
    catalogRepository: CatalogRepository
) : CheckoutBaseViewModel() {

    override val initialState: CheckoutUiState
        get() = CheckoutUiState()

    private val cartItemsFlow = cartRepository.menuItems

    private val addOnItemsCatalogFlow = combine(
            catalogRepository.observeAddOnItems("drinks"),
            catalogRepository.observeAddOnItems("sauces"),
            catalogRepository.observeAddOnItems("ice_creams")
    ) { drinks, sauces, creams ->

        drinks + sauces + creams
    }

    init {
        observeCart()
        observeAddOnItems()
        observeAuthState()
        updateEarliestPickupTime()
    }

    private fun observeCart() {
        cartRepository.menuItems.onEach { cartItems ->
            with(cartItems) {
                updateState {
                    it.copy(
                            menuItems = this,
                            totalAmount = calculateTotal()
                    )
                }
            }
        }
                .launchIn(viewModelScope)
    }

    private fun observeAddOnItems() {

        combine(cartItemsFlow, addOnItemsCatalogFlow) {

            cartItems, addOnItems ->

            val cartItemsIds = cartItems.map { it.id }
                    .toSet()
            addOnItems.filterNot { it.id in cartItemsIds }
                    .shuffled()
        }.onEach { suggested ->

            updateState { it.copy(suggestedAddOnItems = suggested) }
        }
                .launchIn(viewModelScope)
    }

    private fun observeAuthState() {

        authRepository.authState.onEach { authState ->

            updateState { it.copy(isAuthenticated = authState is AuthState.Authenticated) }
        }
                .launchIn(viewModelScope)

    }

    override fun onEvent(event: CheckoutUiEvent) {
        when (event) {

            CheckoutUiEvent.ExpandOrder -> expandOrderDetails()
            CheckoutUiEvent.CollapseOrder -> collapseOrderDetails()

            is CheckoutUiEvent.IncrementQuantity -> incrementQuantity(event.menuItem)
            is CheckoutUiEvent.DecrementQuantity -> decrementQuantity(event.menuItem)
            is CheckoutUiEvent.RemoveItem -> removeCartItem(event.menuItem)

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

            CheckoutUiEvent.PlaceOrder -> placeOrder()

            CheckoutUiEvent.GoBack -> {
                sendActionEvent(CheckoutActionEvent.NavigateBack)
            }

            CheckoutUiEvent.ExitCheckout -> {
                sendActionEvent(CheckoutActionEvent.ExitCheckout)
            }

            CheckoutUiEvent.SignIn -> {
                sendActionEvent(CheckoutActionEvent.NavigateToAuthScreen)
            }
        }
    }

    private fun expandOrderDetails() {
        updateState { it.copy(expanded = true) }
    }

    private fun collapseOrderDetails() {
        updateState { it.copy(expanded = false) }
    }

    private fun incrementQuantity(menuItem: MenuItem) {
        launch {

            cartRepository.updateCount(
                    menuItem = menuItem,
                    newCount = menuItem.counter + 1
            )
        }
    }

    private fun decrementQuantity(menuItem: MenuItem) {
        launch {
            cartRepository.updateCount(
                    menuItem = menuItem,
                    newCount = (menuItem.counter - 1)
                            .coerceAtLeast(minimumValue = 1)
            )
        }
    }

    private fun addItem(addOnItem: AddOnItem) {
        launch {
            cartRepository.addItem(menuItem = addOnItem.toMenuItem())
        }
    }

    private fun removeCartItem(menuItem: MenuItem) {
        launch {
            cartRepository.removeItem(menuItem)
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

    private fun placeOrder() {

        val userId = authRepository.currentUser?.userId ?: return

        val order = Order(
                id = "",
                userId = userId,
                orderNumber = generateOrderNumber(),
                pickupTime = currentState.dateTimePickerState.earliestPickupTime,
                items = currentState.menuItems.map { "${it.counter} x ${it.name}" },
                totalAmount = currentState.menuItems.calculateTotal(),
                status = OrderStatus.IN_PROGRESS,
                timestamp = LocalDateTime.now()
        )

        launch {

            orderRepository.saveOrder(order)
        }

        updateState {
            it.copy(
                    checkoutStep = CheckoutStep.STEP_CONFIRMATION,
                    orderNumber = generateOrderNumber()
            )
        }

        launch {
            cartRepository.clearAuthenticatedCart()
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