package com.tonyxlab.lazypizza.presentation.screens.menu.menu.handling

import com.tonyxlab.lazypizza.presentation.core.base.handling.ActionEvent

sealed interface MenuActionEvent: ActionEvent{

    data object LaunchDialingPad: MenuActionEvent
    data class NavigateToDetailsScreen(val id: Long): MenuActionEvent
    data object NavigateToAuthScreen: MenuActionEvent
}