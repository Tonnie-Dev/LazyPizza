package com.tonyxlab.lazypizza.presentation.screens.details

import com.tonyxlab.lazypizza.domain.model.Topping
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsActionEvent
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsUiEvent
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsUiState
import com.tonyxlab.lazypizza.utils.mockPizzas

typealias DetailsBaseViewModel = BaseViewModel<DetailsUiState, DetailsUiEvent, DetailsActionEvent>

class DetailsViewModel(private val id: Long) : DetailsBaseViewModel() {

    init {
        val pizzaItem = mockPizzas.first { pizza -> pizza.id == this.id }
        updateState {
            it.copy(pizzaStateItem = pizzaItem)
        }

        calculateTotalPrice()
    }

    override val initialState: DetailsUiState
        get() = DetailsUiState()

    override fun onEvent(event: DetailsUiEvent) {
        when (event) {
            is DetailsUiEvent.ClickToppingCard -> {
                addToSelectionList(event.topping)
            }

            is DetailsUiEvent.AddExtraToppings -> {

                addExtraToppings(topping = event.topping)
            }

            is DetailsUiEvent.RemoveExtraToppings -> {

                removeExtraToppings(topping = event.topping)
            }


            DetailsUiEvent.AddToCart -> {}
        }
    }

    private fun addToSelectionList(topping: Topping) {

        if (currentState.selectedToppings.any { it.id == topping.id }) return

        updateTopping(topping, 1)
        calculateTotalPrice()
    }

    private fun addExtraToppings(topping: Topping) {

        val currentCount = currentState.selectedToppings
                .find { it.id == topping.id }?.counter ?: 0

        val newCount = currentCount.plus(1)
                .coerceAtMost(maximumValue = 3)
        updateTopping(topping = topping, newCount = newCount)

        calculateTotalPrice()
    }

    private fun removeExtraToppings(topping: Topping) {

        val currentCount = currentState.selectedToppings
                .find { it.id == topping.id }?.counter ?: 0

        val newCount = currentCount.minus(1)
                .coerceAtLeast(minimumValue = 0)

        updateTopping(topping = topping, newCount = newCount)
        calculateTotalPrice()

    }

    private fun updateTopping(topping: Topping, newCount: Int) {

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