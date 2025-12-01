package com.tonyxlab.lazypizza.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.tonyxlab.lazypizza.presentation.screens.details.DetailsScreen
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

            Scaffold(bottomBar = { BottomNavBar(navigator = navigator) }) {

                Box(
                        modifier = Modifier
                                .fillMaxSize()
                                .padding(it), contentAlignment = Alignment.Center
                ) { Text("Cart Screen") }
            }
        }

        entry<HistoryScreenDestination> {
            Scaffold(bottomBar = { BottomNavBar(navigator = navigator) }) {

                Box(
                        modifier = Modifier
                                .fillMaxSize()
                                .padding(it), contentAlignment = Alignment.Center
                ) { Text("History Screen") }
            }

        }

        entry<DetailScreenDestination> {

            DetailsScreen(id = it.id, navigator = navigator)
        }

    }

    NavDisplay(
            onBack = { navigator.goBack() },
            entries = navigationState.toEntries(entryProvider)
    )
}

/*
    NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = { key ->
                when (key) {
                    is Destinations.HomeScreenDestination -> NavEntry(key) {

                        Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                        ) {

                            Button(
                                    onClick = {
                                        backStack.add(Destinations.DetailScreenDestination(id = "13"))
                                    }
                            ) {
                                Text(
                                        text = "Go To Details"
                                )
                            }
                        }
                    }

                    is Destinations.DetailScreenDestination -> NavEntry(key) {

                        Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Details Screen with key:${key.id}")
                        }

                    }
                }
            }
    )*/
