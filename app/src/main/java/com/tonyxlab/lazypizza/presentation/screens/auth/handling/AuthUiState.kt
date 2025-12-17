package com.tonyxlab.lazypizza.presentation.screens.auth.handling

import androidx.compose.foundation.text.input.TextFieldState
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState

data class AuthUiState(
    val isSignedIn: Boolean = false,
    val textFieldState: TextFieldState = TextFieldState(),
    val continueToLogin: Boolean = false
) : UiState
