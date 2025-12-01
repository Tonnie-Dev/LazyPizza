package com.tonyxlab.lazypizza.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreenDestination : NavKey

@Serializable
data class DetailScreenDestination(val id: Long) : NavKey

@Serializable
data object MenuScreenDestination : NavKey

@Serializable
data object CartScreenDestination : NavKey

@Serializable
data object HistoryScreenDestination : NavKey

