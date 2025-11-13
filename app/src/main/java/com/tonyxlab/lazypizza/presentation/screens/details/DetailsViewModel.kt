package com.tonyxlab.lazypizza.presentation.screens.details

import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsActionEvent
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsUiEvent
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsUiState

typealias DetailsBaseViewModel = BaseViewModel<DetailsUiState, DetailsUiEvent, DetailsActionEvent>
class DetailsViewModel: DetailsBaseViewModel() {

    override val initialState: DetailsUiState
        get() = DetailsUiState()

    override fun onEvent(event: DetailsUiEvent) {
        TODO("Not yet implemented")
    }
}