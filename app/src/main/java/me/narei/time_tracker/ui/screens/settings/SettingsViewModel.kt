package me.narei.time_tracker.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.narei.time_tracker.data.TimeEntryDao
import me.narei.time_tracker.data.category.Category
import me.narei.time_tracker.data.category.CategoryPreferencesManager

class SettingsViewModel(
    private val timeEntryDao: TimeEntryDao,
    private val categoryPreferencesManager: CategoryPreferencesManager
) : ViewModel() {

    val hiddenCategories: StateFlow<Set<Category>?> = categoryPreferencesManager.hiddenCategoriesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun toggleCategory(category: Category) {
        viewModelScope.launch {
            categoryPreferencesManager.toggleHiddenCategory(category)
        }
    }

}