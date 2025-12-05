package com.tonyxlab.lazypizza.navigation

import androidx.navigation3.runtime.NavKey

class Navigator(val state: NavigationState) {
    fun navigate(route: NavKey) {

        if (route in state.backStacks.keys) {

            // SWITCH TAB
            state.topLevelRoute = route

            // CLEAR ITS HISTORY
            val stack = state.backStacks[route]
            stack?.apply {
                clear()
                add(route)
            }
        } else {

            // USER NAVIGATED TO INNER SCREEN (Details)
            state.backStacks[state.topLevelRoute]?.add(route)
        }
    }

    fun goBack() {

        val currentStack = state.backStacks[state.topLevelRoute] ?: return

        // Only pop inner screens
        if (currentStack.size > 1) {
            currentStack.removeLastOrNull()
        }
    }
}
