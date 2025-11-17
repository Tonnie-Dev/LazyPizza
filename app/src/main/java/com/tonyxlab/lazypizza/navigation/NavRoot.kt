package com.tonyxlab.lazypizza.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.tonyxlab.lazypizza.presentation.screens.details.DetailsScreen
import com.tonyxlab.lazypizza.presentation.screens.home.HomeScreen

@Composable
fun NavRoot(modifier: Modifier = Modifier) {

    val backStack = rememberNavBackStack(Destinations.HomeScreenDestination)
    val navOperations = NavOperations(backStack = backStack)

    NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = listOf(
                    rememberSceneSetupNavEntryDecorator(),
                    rememberSavedStateNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()
                    ),
            entryProvider = entryProvider {
                entry<Destinations.HomeScreenDestination> {
                    HomeScreen(navOperations = navOperations)
                }

                entry<Destinations.DetailScreenDestination> {
                    DetailsScreen(id = it.id, navOperations = navOperations)
                }
            }
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
