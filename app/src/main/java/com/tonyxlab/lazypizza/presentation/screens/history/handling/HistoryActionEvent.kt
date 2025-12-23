package com.tonyxlab.lazypizza.presentation.screens.history.handling

import com.tonyxlab.lazypizza.presentation.core.base.handling.ActionEvent

sealed interface HistoryActionEvent: ActionEvent{

    data object NavigateToAuth: HistoryActionEvent
}