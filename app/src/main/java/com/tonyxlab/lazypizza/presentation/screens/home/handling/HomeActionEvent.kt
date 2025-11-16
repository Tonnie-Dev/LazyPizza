package com.tonyxlab.lazypizza.presentation.screens.home.handling

import com.tonyxlab.lazypizza.presentation.core.base.handling.ActionEvent

sealed interface HomeActionEvent: ActionEvent{

    data object LaunchDialingPad: HomeActionEvent
    data class NavigateToDetailsScreen(val id: Long): HomeActionEvent
}