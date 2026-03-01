package me.narei.time_tracker.ui.screens.time_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.narei.time_tracker.data.TimeEntry
import me.narei.time_tracker.data.TimeEntryDao
import me.narei.time_tracker.data.category.Category
import me.narei.time_tracker.data.category.CategoryPreferencesManager
import java.time.LocalDate

class TimeListViewModel(
    private val timeEntryDao: TimeEntryDao,
    private val categoryPreferencesManager: CategoryPreferencesManager
) : ViewModel() {

    private val _currentDate = MutableStateFlow(LocalDate.now())
    val currentDate: StateFlow<LocalDate> = _currentDate.asStateFlow()

    val hiddenCategories: StateFlow<Set<Category>?> = categoryPreferencesManager.hiddenCategoriesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val activeTimeEntry: StateFlow<TimeEntry?> = timeEntryDao.getActiveTimeEntry()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val timeEntries = _currentDate
        .flatMapLatest { date ->
            timeEntryDao.getEntriesForDate(date)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun changeDate(date: LocalDate) {
        _currentDate.value = date
    }

    fun saveTimeEntry(entry: TimeEntry) {
        viewModelScope.launch {
            timeEntryDao.upsertTimeEntry(entry)
        }
    }

    fun deleteTimeEntry(entry: TimeEntry) {
        viewModelScope.launch {
            timeEntryDao.deleteTimeEntry(entry)
        }
    }

}