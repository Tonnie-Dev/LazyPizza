package com.tonyxlab.lazypizza.presentation.screens.history

import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.history.handling.HistoryActionEvent
import com.tonyxlab.lazypizza.presentation.screens.history.handling.HistoryUiEvent
import com.tonyxlab.lazypizza.presentation.screens.history.handling.HistoryUiState

typealias HistoryBaseViewModel = BaseViewModel<HistoryUiState, HistoryUiEvent, HistoryActionEvent>

class HistoryViewModel: HistoryBaseViewModel (){

    override val initialState: HistoryUiState
        get() = HistoryUiState()

    override fun onEvent(event: HistoryUiEvent) {
        TODO("Not yet implemented")
    }
}