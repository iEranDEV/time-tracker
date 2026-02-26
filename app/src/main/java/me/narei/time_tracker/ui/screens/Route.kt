package me.narei.time_tracker.ui.screens

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object TimeList : Route, NavKey

    @Serializable
    data object Settings : Route, NavKey

}
