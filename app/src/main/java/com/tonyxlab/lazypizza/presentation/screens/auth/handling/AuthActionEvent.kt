package com.tonyxlab.lazypizza.presentation.screens.auth.handling

import com.tonyxlab.lazypizza.presentation.core.base.handling.ActionEvent

interface AuthActionEvent : ActionEvent{

    data object NavigateBackToMenu: AuthActionEvent
    data object NavigateToOtp: AuthActionEvent
}