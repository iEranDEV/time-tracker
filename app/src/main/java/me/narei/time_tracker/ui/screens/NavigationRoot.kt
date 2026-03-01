package me.narei.time_tracker.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import me.narei.time_tracker.ui.screens.settings.SettingsScreen
import me.narei.time_tracker.ui.screens.settings.SettingsViewModel
import me.narei.time_tracker.ui.screens.time_list.TimeListScreen
import me.narei.time_tracker.ui.screens.time_list.TimeListViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.collections.listOf

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationRoot() {

    val backstack = rememberNavBackStack(Route.TimeList)

    NavDisplay(
        transitionSpec = {
            slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
        },
        popTransitionSpec = {
            slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
        },
        predictivePopTransitionSpec = {
            slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
        },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        backStack = backstack,
        entryProvider = entryProvider {
            entry<Route.TimeList> {

                val viewModel: TimeListViewModel = koinViewModel()

                val currentDate by viewModel.currentDate.collectAsStateWithLifecycle()
                val timeEntries by viewModel.timeEntries.collectAsStateWithLifecycle()
                val activeTimeEntry by viewModel.activeTimeEntry.collectAsStateWithLifecycle()
                val hiddenCategories by viewModel.hiddenCategories.collectAsStateWithLifecycle()

                TimeListScreen(
                    onNavigateToSettings = { backstack.add(Route.Settings) },
                    timeEntries = timeEntries,
                    currentDate = currentDate,
                    changeCurrentDate = { date -> viewModel.changeDate(date) },
                    activeTimeEntry = activeTimeEntry,
                    deleteTimeEntry = { entry -> viewModel.deleteTimeEntry(entry) },
                    saveTimeEntry = { entry -> viewModel.saveTimeEntry(entry) },
                    hiddenCategories = hiddenCategories
                )
            }
            entry<Route.Settings> {

                val viewModel: SettingsViewModel = koinViewModel()

                val hiddenCategories by viewModel.hiddenCategories.collectAsStateWithLifecycle()

                SettingsScreen(
                    onNavigateBack = { if (backstack.size > 1) { backstack.removeAt(backstack.size - 1) } },
                    hiddenCategories = hiddenCategories,
                    toggleCategory = { category -> viewModel.toggleCategory(category) },
                    deleteData = { viewModel.deleteData() }
                )
            }
        }
    )

}