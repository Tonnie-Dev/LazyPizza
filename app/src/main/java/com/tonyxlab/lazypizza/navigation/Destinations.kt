package com.tonyxlab.lazypizza.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class Destinations: NavKey{

    @Serializable
    data object HomeScreenDestination: Destinations()

    @Serializable
    data class DetailScreenDestination(val id: String): Destinations()
}