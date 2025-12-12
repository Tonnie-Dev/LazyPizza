package com.tonyxlab.lazypizza.presentation.screens.history.handling

import com.tonyxlab.lazypizza.presentation.core.base.handling.UiEvent

sealed interface HistoryUiEvent: UiEvent{

    data object SignIn: HistoryUiEvent
}