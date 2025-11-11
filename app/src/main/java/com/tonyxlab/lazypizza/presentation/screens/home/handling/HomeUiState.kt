package com.tonyxlab.lazypizza.presentation.screens.home.handling

import androidx.compose.runtime.Stable
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState

@Stable
data class HomeUiState(val phoneNumber:String = "+1 (555) 321-7890"): UiState{}