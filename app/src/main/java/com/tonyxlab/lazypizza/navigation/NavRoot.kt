package com.tonyxlab.lazypizza.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.tonyxlab.lazypizza.presentation.screens.auth.AuthScreen
import com.tonyxlab.lazypizza.presentation.screens.cart.cart.CartScreen
import com.tonyxlab.lazypizza.presentation.screens.menu.details.DetailsScreen
import com.tonyxlab.lazypizza.presentation.screens.history.HistoryScreen
import com.tonyxlab.lazypizza.presentation.screens.menu.menu.MenuScreen

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
            startRoute = MenuScreenDestination,
            topLevelRoutes = topLevelRoutes
    )

    val navigator = remember { Navigator(navigationState) }

    val entryProvider = entryProvider {

        entry<MenuScreenDestination> {
            MenuScreen(navigator = navigator)
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
            AuthScreen(navigator = navigator)
        }
    }

    NavDisplay(
            onBack = { navigator.goBack() },
            entries = navigationState.toEntries(entryProvider)
    )
}


