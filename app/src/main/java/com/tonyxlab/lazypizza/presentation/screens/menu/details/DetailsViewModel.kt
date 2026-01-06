package com.tonyxlab.lazypizza.presentation.screens.menu.details

import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.domain.model.Topping
import com.tonyxlab.lazypizza.domain.model.toCartItem
import com.tonyxlab.lazypizza.domain.repository.CartRepository
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.menu.details.handling.DetailsActionEvent
import com.tonyxlab.lazypizza.presentation.screens.menu.details.handling.DetailsUiEvent
import com.tonyxlab.lazypizza.presentation.screens.menu.details.handling.DetailsUiState
import com.tonyxlab.lazypizza.utils.mockPizzas
import kotlinx.coroutines.delay

typealias DetailsBaseViewModel = BaseViewModel<DetailsUiState, DetailsUiEvent, DetailsActionEvent>

class DetailsViewModel(
    private val id: Long,
    private val repository: CartRepository
) : DetailsBaseViewModel() {

    init {
        loadPizza()

        calculateTotalPrice()
    }

    override val initialState: DetailsUiState
        get() = DetailsUiState()

    override fun onEvent(event: DetailsUiEvent) {
        when (event) {
            is DetailsUiEvent.ClickToppingCard -> {
                selectTopping(event.topping)
            }

            is DetailsUiEvent.AddExtraToppings -> {

                incrementTopping(topping = event.topping)
            }

            is DetailsUiEvent.RemoveExtraToppings -> {

                decrementTopping(topping = event.topping)
            }

            DetailsUiEvent.AddToCart -> addPizzaToCart()
        }
    }

    private fun loadPizza() {

        val pizzaItem = mockPizzas.first { it.id == this.id }
        updateState { it.copy(pizzaStateItem = pizzaItem) }
    }

    private fun addPizzaToCart() {

        val cartItem = currentState.pizzaStateItem!!.toCartItem()
                .copy(toppings = currentState.selectedToppings.toList())

        launch {

            repository.addItem(cartItem)

        }

        sendActionEvent(
                actionEvent = DetailsActionEvent.ShowSnackbar(
                        messageRes = R.string.snack_text_item_added_to_cart
                )
        )

        launch {
            delay(750)
            sendActionEvent(actionEvent = DetailsActionEvent.NavigateBackToMenu)
        }
    }

    private fun selectTopping(topping: Topping) {

        if (currentState.selectedToppings.any { it.id == topping.id }) return
        updateSelectedToppingsList(topping, 1)
        calculateTotalPrice()
    }

    private fun incrementTopping(topping: Topping) {

        val currentCount = currentState.selectedToppings
                .find { it.id == topping.id }?.counter ?: 0

        val newCount = currentCount.plus(1)
                .coerceAtMost(maximumValue = 3)
        updateSelectedToppingsList(topping = topping, newCount = newCount)

        calculateTotalPrice()
    }

    private fun decrementTopping(topping: Topping) {

        val currentCount = currentState.selectedToppings
                .find { it.id == topping.id }?.counter ?: 0

        val newCount = currentCount.minus(1)
                .coerceAtLeast(minimumValue = 0)

        updateSelectedToppingsList(topping = topping, newCount = newCount)
        calculateTotalPrice()

    }

    private fun updateSelectedToppingsList(topping: Topping, newCount: Int) {

        val newSet = currentState.selectedToppings.toMutableSet()

        newSet.removeIf { it.id == topping.id }

        if (newCount > 0) {
            newSet.add(topping.copy(counter = newCount))
        }
        updateState { it.copy(selectedToppings = newSet) }

    }

    private fun calculateTotalPrice() {

        val basePrice = currentState.pizzaStateItem?.price ?: 0.00

        val toppingsTotal = currentState.selectedToppings.sumOf {
            it.toppingPrice * it.counter
        }
        updateState { it.copy(aggregatePrice = basePrice + toppingsTotal) }
    }
}