package me.narei.time_tracker.ui.screens.time_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.narei.time_tracker.data.TimeEntry
import me.narei.time_tracker.ui.components.shared.SwipeableWithActions
import me.narei.time_tracker.ui.components.TimeEntryCard
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import me.narei.time_tracker.ui.components.CalendarButtonWithPopup
import me.narei.time_tracker.ui.components.TimeEntryTimer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeListScreen(
    modifier: Modifier = Modifier,
    onNavigateToSettings: () -> Unit,
    timeEntries: List<TimeEntry> = emptyList(),
    currentDate: LocalDate = LocalDate.now(),
    changeCurrentDate: (LocalDate) -> Unit,
    activeTimeEntry: TimeEntry? = null,
    deleteTimeEntry: (TimeEntry) -> Unit,
    saveTimeEntry: (TimeEntry) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        fontWeight = FontWeight.Medium
                    )
                },
                actions = {
                    CalendarButtonWithPopup(
                        currentDate = currentDate,
                        onDateSelected = { date ->
                            changeCurrentDate(date)
                        }
                    )
                    IconButton( onClick = onNavigateToSettings ) {
                        Icon(
                            imageVector = Icons.Rounded.Settings,
                            contentDescription = "Go to settings"
                        )
                    }
                }
            )
        },
        bottomBar = {
            TimeEntryTimer(
                entry = activeTimeEntry,
                saveTimeEntry = saveTimeEntry
            )
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            if (timeEntries.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No time entries added yet")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = timeEntries,
                        key = { timeEntry -> timeEntry.id!! }
                    ) { timeEntry ->
                        SwipeableWithActions(
                            content = { TimeEntryCard( entry = timeEntry ) },
                            actions = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(MaterialTheme.colorScheme.error)
                                        .padding(horizontal = 12.dp)
                                        .clickable { deleteTimeEntry(timeEntry) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Delete,
                                        contentDescription = "Delete time entry with id ${timeEntry.id}",
                                        tint = MaterialTheme.colorScheme.onError
                                    )
                                }
                            }
                        )
                    }
                }
            }

        }

    }

}