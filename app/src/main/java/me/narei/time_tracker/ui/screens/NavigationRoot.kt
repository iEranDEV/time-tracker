package me.narei.time_tracker.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import kotlin.collections.listOf

@Composable
fun NavigationRoot() {

    val backstack = rememberNavBackStack(Route.TimeList)

    NavDisplay(
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        backStack = backstack,
        entryProvider = entryProvider {
            entry<Route.TimeList> {
                TimeListScreen(
                    onNavigateToSettings = { backstack.add(Route.Settings) }
                )
            }
            entry<Route.Settings> {
                SettingsScreen()
            }
        }
    )

}