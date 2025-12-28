package com.tonyxlab.lazypizza.presentation.screens.auth.handling

import android.app.Activity
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiEvent

sealed interface AuthUiEvent: UiEvent{

    data class ContinueToLoginIn(val activity: Activity): AuthUiEvent
    data object ContinueWithoutLogin: AuthUiEvent
    data object ConfirmOtp: AuthUiEvent
}
