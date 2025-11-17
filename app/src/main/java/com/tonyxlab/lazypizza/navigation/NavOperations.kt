package com.tonyxlab.lazypizza.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack

class NavOperations(val backStack: NavBackStack) {

    fun navigateToHomeScreen() {
        backStack.add(Destinations.HomeScreenDestination)
    }

    fun navigateToDetailsScreen(id: Long) {
        backStack.add(Destinations.DetailScreenDestination(id = id))
    }


}

@Composable
fun rememberNavOperations(backStack: NavBackStack): NavOperations {

    return remember { NavOperations(backStack = backStack) }
}