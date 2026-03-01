package me.narei.time_tracker.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import me.narei.time_tracker.ui.screens.settings.SettingsScreen
import me.narei.time_tracker.ui.screens.time_list.TimeListScreen
import me.narei.time_tracker.ui.screens.time_list.TimeListViewModel
import kotlin.collections.listOf

@Composable
fun NavigationRoot() {

    val backstack = rememberNavBackStack(Route.TimeList)
    val context = LocalContext.current

    NavDisplay(
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        backStack = backstack,
        entryProvider = entryProvider {
            entry<Route.TimeList> {

                val viewModel: TimeListViewModel = viewModel( factory = TimeListViewModel.provideFactory(context) )

                val currentDate by viewModel.currentDate.collectAsStateWithLifecycle()
                val timeEntries by viewModel.timeEntries.collectAsStateWithLifecycle()
                val activeTimeEntry by viewModel.activeTimeEntry.collectAsStateWithLifecycle()

                TimeListScreen(
                    onNavigateToSettings = { backstack.add(Route.Settings) },
                    timeEntries = timeEntries,
                    currentDate = currentDate,
                    changeCurrentDate = { date ->
                        viewModel.changeDate(date)
                    },
                    activeTimeEntry = activeTimeEntry,
                    deleteTimeEntry = { entry ->
                        viewModel.deleteTimeEntry(entry)
                    },
                    saveTimeEntry = { entry ->
                        viewModel.saveTimeEntry(entry)
                    }
                )
            }
            entry<Route.Settings> {
                SettingsScreen(
                    onNavigateBack = { backstack.removeLast() }
                )
            }
        }
    )

}