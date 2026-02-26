package me.narei.time_tracker.ui.screens.time_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.narei.time_tracker.data.TimeEntry
import java.time.LocalDate

class TimeListViewModel : ViewModel() {

    private val _currentDate = MutableStateFlow(LocalDate.now())
    val currentDate: StateFlow<LocalDate> = _currentDate.asStateFlow()

    private val _timeEntries = MutableStateFlow<List<TimeEntry>>(emptyList())
    val timeEntries = _timeEntries.asStateFlow()

    init {
        loadTimeEntries()
    }

    fun changeDate(date: LocalDate) {
        _currentDate.value = date
    }

    private fun loadTimeEntries() {
        _timeEntries.value = listOf(
            /*
            TimeEntry(
                name = "Testowe zadanie",
                startTime = 1772118000,
                endTime = 1772119800
            )
            */
        )
    }

}