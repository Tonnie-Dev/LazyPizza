package com.tonyxlab.lazypizza.presentation.screens.auth.handling

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState

data class AuthUiState(
    val isLoading: Boolean =false,
    val isSignedIn: Boolean = false,
    val otpRequestId: Int = 0,
    val phoneInputState: PhoneInputState = PhoneInputState(),
    val otpInputState: OtpInputState = OtpInputState(),
    val authScreenStep: AuthScreenStep = AuthScreenStep.PhoneInputStep
) : UiState {

    @Stable
    data class PhoneInputState(
        val textFieldState: TextFieldState = TextFieldState(initialText = "+254723445813"),
        val continueEnabled: Boolean = false
    )

    @Stable
    data class OtpInputState(
        val textFieldState: TextFieldState = TextFieldState(),
        val secondsRemaining: Int = 10,
        val resend: Boolean = false,
        val confirmEnabled: Boolean = false,
        val error: Boolean = false
    )

    @Stable
    enum class AuthScreenStep { PhoneInputStep, OtpInputStep }
}

