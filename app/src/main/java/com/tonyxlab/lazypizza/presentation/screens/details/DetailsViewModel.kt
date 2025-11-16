package com.tonyxlab.lazypizza.presentation.screens.details

import androidx.lifecycle.SavedStateHandle
import com.tonyxlab.lazypizza.domain.model.Topping
import com.tonyxlab.lazypizza.navigation.Destinations
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsActionEvent
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsUiEvent
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsUiState
import timber.log.Timber

typealias DetailsBaseViewModel = BaseViewModel<DetailsUiState, DetailsUiEvent, DetailsActionEvent>

class DetailsViewModel(private val id: Long) : DetailsBaseViewModel() {

    init {
        Timber.tag("DetailsVW").i("Transported Id is: $id ")
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