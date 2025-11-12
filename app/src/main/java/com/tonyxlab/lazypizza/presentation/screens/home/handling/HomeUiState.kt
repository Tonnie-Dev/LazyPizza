package com.tonyxlab.lazypizza.presentation.screens.home.handling

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState

@Stable
data class HomeUiState(
    val phoneNumber: String = "+1 (555) 321-7890",
    val textFieldState: TextFieldState = TextFieldState(),
    val isTextEmpty: Boolean = false
) : UiState {}