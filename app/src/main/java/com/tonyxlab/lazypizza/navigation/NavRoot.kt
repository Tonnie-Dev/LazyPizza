package com.tonyxlab.lazypizza.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.tonyxlab.lazypizza.presentation.screens.auth.AuthScreen
import com.tonyxlab.lazypizza.presentation.screens.cart.CartScreen
import com.tonyxlab.lazypizza.presentation.screens.details.DetailsScreen
import com.tonyxlab.lazypizza.presentation.screens.history.HistoryScreen
import com.tonyxlab.lazypizza.presentation.screens.home.HomeScreen

@Composable
fun NavRoot() {

    val topLevelRoutes = remember {
        setOf(
                MenuScreenDestination,
                CartScreenDestination,
                HistoryScreenDestination
        )
    }

    val navigationState = rememberNavigationState(
            startRoute = CartScreenDestination,
            topLevelRoutes = topLevelRoutes
    )

    val navigator = remember { Navigator(navigationState) }

    val entryProvider = entryProvider {

        entry<MenuScreenDestination> {
            HomeScreen(navigator = navigator)
        }

        entry<CartScreenDestination> {
            CartScreen(navigator = navigator)
        }

        entry<HistoryScreenDestination> {
            HistoryScreen(navigator = navigator)
        }

        entry<DetailScreenDestination> {
            DetailsScreen(id = it.id, navigator = navigator)
        }

        entry< AuthScreenDestination> {
            AuthScreen()
        }
    }

    NavDisplay(
            onBack = { navigator.goBack() },
            entries = navigationState.toEntries(entryProvider)
    )
}


