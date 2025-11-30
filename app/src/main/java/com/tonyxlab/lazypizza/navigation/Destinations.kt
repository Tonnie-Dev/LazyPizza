package com.tonyxlab.lazypizza.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class Destinations : NavKey {

    @Serializable
    data object HomeScreenDestination : Destinations()

    @Serializable
    data class DetailScreenDestination(val id: Long) : Destinations()

  /*  @Serializable
    data object MenuScreenDestination : Destinations()

    @Serializable
    data object CartScreenDestination : Destinations()

    @Serializable
    data object HistoryScreenDestination : Destinations()*/
}







    @Serializable
    data object MenuScreenDestination : NavKey

    @Serializable
    data object CartScreenDestination: NavKey

    @Serializable
    data object HistoryScreenDestination: NavKey

