package com.tonyxlab.lazypizza.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlin.collections.removeLastOrNull

class Navigator(val state: NavigationState) {
    fun navigate(route: NavKey) {

        if (route in state.backStacks.keys) {
            state.topLevelRoute = route
        } else {
            state.backStacks[state.topLevelRoute]?.add(route)
        }
    }

    fun goBack() {

        val currentStack = state.backStacks[state.topLevelRoute] ?: return
        val currentRoute = currentStack.lastOrNull() ?: return

        if (currentRoute == state.topLevelRoute) {
            state.topLevelRoute = state.startRoute
        } else {
            currentStack.removeLastOrNull()
        }
    }
}
class NavOperations(val backStack: NavBackStack<NavKey>) {

    fun navigateToHomeScreen() {
        backStack.add(HomeScreenDestination)
    }

    fun navigateToDetailsScreen(id: Long) {
        backStack.add(DetailScreenDestination(id = id))
    }

    fun popBackStack(){

        backStack.removeLastOrNull()
    }
}

@Composable
fun rememberNavOperations(backStack: NavBackStack<NavKey>): NavOperations {

    return remember { NavOperations(backStack = backStack) }
}