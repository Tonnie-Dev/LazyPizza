package com.tonyxlab.lazypizza.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay

@Composable
fun HostContainer(modifier: Modifier = Modifier) {

  /*  val backStack = remember {
        mutableListOf<Destinations>(Destinations.HomeScreenDestination)
    }*/

   val backStack = rememberNavBackStack(Destinations.HomeScreenDestination)

    NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
          entryProvider = entryProvider {

              entry <Destinations.HomeScreenDestination>{

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

              entry<Destinations.DetailScreenDestination>{

                  Box(
                          modifier = Modifier.fillMaxSize(),
                          contentAlignment = Alignment.Center
                  ) {

                      Text( text = "The Id is: ${it.id}")

                  }

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
