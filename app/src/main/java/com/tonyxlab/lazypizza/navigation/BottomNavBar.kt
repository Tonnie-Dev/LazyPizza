package com.tonyxlab.lazypizza.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator

@Composable
fun BottomNavBar(
    navigationState: NavigationState,
    modifier: Modifier = Modifier
) {

    val topLevelRoutes = remember {
        setOf<NavKey>(
                MenuScreenDestination,
                CartScreenDestination,
                HistoryScreenDestination
        )
    }

    /*
        val navigationState = rememberNavigationState(
                startRoute = CartScreenDestination,
                topLevelRoutes = topLevelRoutes
        )
    */

    val navigator = remember { Navigator(navigationState) }
    NavigationBar {

        NavigationBarItem(
                selected = navigationState.topLevelRoute == MenuScreenDestination,
                onClick = { navigator.navigate(MenuScreenDestination) },
                icon = { Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu") },
                label = { Text(text = "Menu") }
        )


        NavigationBarItem(
                selected = navigationState.topLevelRoute == CartScreenDestination,
                onClick = { navigator.navigate(CartScreenDestination) },
                icon = { Icon(imageVector = Icons.Default.Menu, contentDescription = "Cart") },
                label = { Text(text = "Cart") }
        )


        NavigationBarItem(
                selected = navigationState.topLevelRoute == HistoryScreenDestination,
                onClick = { navigator.navigate(HistoryScreenDestination) },
                icon = { Icon(imageVector = Icons.Default.Menu, contentDescription = "History") },
                label = { Text(text = "History") }
        )
    }
}

@Composable
fun rememberNavigationState(
    startRoute: NavKey,
    topLevelRoutes: Set<NavKey>
): NavigationState {

    // val topLevelRoute = rememberSaveable { mutableStateOf(startRoute) }
    val topLevelRoute = remember { mutableStateOf(startRoute) }

    val backStacks = topLevelRoutes.associateWith { key ->

        rememberNavBackStack(key)

    }

    return remember(startRoute, topLevelRoutes) {
        NavigationState(
                startRoute = startRoute,
                topLevelRoute = topLevelRoute,
                backStacks = backStacks
        )
    }
}

class NavigationState(
    val startRoute: NavKey,
    topLevelRoute: MutableState<NavKey>,
    val backStacks: Map<NavKey, NavBackStack<NavKey>>
) {

    var topLevelRoute: NavKey by topLevelRoute

    val stacksInUse: List<NavKey>
        get() = if (topLevelRoute == startRoute) {
            listOf(startRoute)
        } else {
            listOf(startRoute, topLevelRoute)
        }
}



@Composable
fun NavigationState.toEntries(entryProvider: (NavKey) -> NavEntry<NavKey>): SnapshotStateList<NavEntry<NavKey>> {

    val decorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator<NavKey>()

    )

    val decoratedEntries = backStacks.mapValues {

        (_, stack) ->

        rememberDecoratedNavEntries(
                backStack = stack,
                entryDecorators = decorators,
                entryProvider = entryProvider
        )
    }

    return stacksInUse
            .flatMap {
                decoratedEntries[it] ?: emptyList()
            }
            .toMutableStateList()
}

/*
sealed interface NavOptions {

    data class MenuNavOption(
        val destination: Destinations,
        val label: String,
        @DrawableRes val iconRes: Int
    ) : NavOptions

    data class CartNavOption(
        val destination: Destinations,
        val label: String,
        @DrawableRes val iconRes: Int,
        val itemCount: Int
    ) : NavOptions

    data class HistoryNavOption(
        val destination: Destinations,
        val label: String,
        @DrawableRes val iconRes: Int
    ) : NavOptions
}*/
