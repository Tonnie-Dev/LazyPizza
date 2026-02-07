package com.tonyxlab.lazypizza.presentation.screens.history.handling

import com.tonyxlab.lazypizza.domain.model.Order
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiState

data class HistoryUiState(
    val badgeCount:Int = 0,
    val isSignedIn: Boolean = false,
        val orderItems: List<Order> = emptyList()
) : UiState
