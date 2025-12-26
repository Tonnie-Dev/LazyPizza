package com.tonyxlab.lazypizza.presentation.screens.auth.handling

import com.tonyxlab.lazypizza.presentation.core.base.handling.UiEvent

sealed interface AuthUiEvent: UiEvent{

    data object ContinueToLoginIn: AuthUiEvent
    data object ContinueWithoutLogin: AuthUiEvent
    data object ConfirmOtp: AuthUiEvent
}
