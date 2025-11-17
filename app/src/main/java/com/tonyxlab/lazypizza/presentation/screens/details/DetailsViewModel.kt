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
        updateState {
            val pizzaItem = mockPizzas.first { pizza -> pizza.id == this.id }
            it.copy(pizzaStateItem = pizzaItem)
        }
    }

    override val initialState: DetailsUiState
        get() = DetailsUiState()

    override fun onEvent(event: DetailsUiEvent) {
        when (event) {
            is DetailsUiEvent.ClickToppingCard -> addToSelectionList(event.topping)
            DetailsUiEvent.AddExtraToppings -> TODO()
            DetailsUiEvent.RemoveExtraToppings -> TODO()
            DetailsUiEvent.SelectToppings -> TODO()
        }
    }

    private fun addToSelectionList(topping: Topping) {
        updateState {
            it.copy(selectedToppings = currentState.selectedToppings.plus(topping))
        }
    }
}