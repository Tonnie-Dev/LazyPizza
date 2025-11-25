package com.tonyxlab.lazypizza.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlin.collections.removeLastOrNull

class NavOperations(val backStack: NavBackStack<NavKey>) {

    fun navigateToHomeScreen() {
        backStack.add(Destinations.HomeScreenDestination)
    }

    fun navigateToDetailsScreen(id: Long) {
        backStack.add(Destinations.DetailScreenDestination(id = id))
    }

    fun popBackStack(){

        backStack.removeLastOrNull()
    }
}

@Composable
fun rememberNavOperations(backStack: NavBackStack<NavKey>): NavOperations {

    return remember { NavOperations(backStack = backStack) }
}