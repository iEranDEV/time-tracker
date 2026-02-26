package me.narei.time_tracker.ui.screens.time_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
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
import me.narei.time_tracker.ui.components.SwipeableWithActions
import me.narei.time_tracker.ui.components.TimeEntryCard
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import me.narei.time_tracker.util.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeListScreen(
    modifier: Modifier = Modifier,
    onNavigateToSettings: () -> Unit,
    timeEntries: List<TimeEntry> = emptyList(),
    currentDate: LocalDate = LocalDate.now()
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("time-tracker-app") },
                actions = {
                    IconButton( onClick = onNavigateToSettings ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Go to settings"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text(
                text = currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            )

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
                    items(timeEntries) { timeEntry ->
                        SwipeableWithActions(
                            content = {
                                TimeEntryCard(
                                    name = timeEntry.name,
                                    startTime = timeEntry.startTime.toLocalDateTime(),
                                    endTime = timeEntry.endTime!!.toLocalDateTime()
                                )
                            },
                            actions = {
                                Button(onClick = { /*TODO*/ }) {
                                    Text("Delete")
                                }
                            }
                        )
                    }
                }
            }

        }

    }

}