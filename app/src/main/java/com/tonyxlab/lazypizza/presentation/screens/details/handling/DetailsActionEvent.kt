package com.tonyxlab.lazypizza.presentation.screens.details.handling

import com.tonyxlab.lazypizza.presentation.core.base.handling.ActionEvent

sealed interface DetailsActionEvent: ActionEvent{

    data object NavigateBackToMenu: DetailsActionEvent
}