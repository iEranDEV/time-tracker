package me.narei.time_tracker.ui.screens.time_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.narei.time_tracker.data.TimeEntry
import me.narei.time_tracker.data.TimeEntryDao
import me.narei.time_tracker.data.category.Category
import me.narei.time_tracker.data.category.CategoryPreferencesManager
import java.time.LocalDate

data class TimeListUiState (
    val currentDate: LocalDate = LocalDate.now(),
    val timeEntries: List<TimeEntry> = emptyList(),
    val activeTimeEntry: TimeEntry? = null,
    val hiddenCategories: Set<Category>? = null,
    val draftName: String = "",
    val draftCategory: Category = Category.OTHER
)


class TimeListViewModel(
    private val timeEntryDao: TimeEntryDao,
    private val categoryPreferencesManager: CategoryPreferencesManager
) : ViewModel() {

    private val _draftName = MutableStateFlow("")
    private val _draftCategory = MutableStateFlow<Category>(Category.OTHER)

    private val _currentDate = MutableStateFlow(LocalDate.now())

    private val _draftState = combine(_draftName, _draftCategory) { name, category ->
        Pair(name, category)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<TimeListUiState> = combine(
        _currentDate,
        _currentDate.flatMapLatest { timeEntryDao.getEntriesForDate(it) },
        timeEntryDao.getActiveTimeEntry(),
        categoryPreferencesManager.hiddenCategoriesFlow,
        _draftState
    ) { date, entries, active, hidden, draft ->
        TimeListUiState(
            currentDate = date,
            timeEntries = entries,
            activeTimeEntry = active,
            hiddenCategories = hidden,
            draftName = draft.first,
            draftCategory = draft.second
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TimeListUiState()
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

    fun setDraft(name: String, category: Category) {
        val active = uiState.value.activeTimeEntry
        if (active != null) {
            saveTimeEntry(active.copy(name = name, category = category))
        } else {
            _draftName.value = name
            _draftCategory.value = category
        }
    }

}