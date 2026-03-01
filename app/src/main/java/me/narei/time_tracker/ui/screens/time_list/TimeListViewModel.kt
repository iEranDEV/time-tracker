package me.narei.time_tracker.ui.screens.time_list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.narei.time_tracker.data.AppDatabase
import me.narei.time_tracker.data.TimeEntry
import me.narei.time_tracker.data.TimeEntryDao
import java.time.LocalDate

class TimeListViewModel(
    private val timeEntryDao: TimeEntryDao
) : ViewModel() {

    private val _currentDate = MutableStateFlow(LocalDate.now())
    val currentDate: StateFlow<LocalDate> = _currentDate.asStateFlow()

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

    companion object {
        fun provideFactory(context: Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val dao = AppDatabase.getDatabase(context.applicationContext).timeEntryDao()

                    @Suppress("UNCHECKED_CAST")
                    return TimeListViewModel(dao) as T
                }
            }
    }

}