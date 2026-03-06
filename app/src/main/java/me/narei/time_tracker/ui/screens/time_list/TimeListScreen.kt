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
import me.narei.time_tracker.ui.components.shared.SwipeableWithActions
import me.narei.time_tracker.ui.components.TimeEntryCard
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.narei.time_tracker.ui.components.CalendarButtonWithPopup
import me.narei.time_tracker.ui.components.TimeEntryTimer
import me.narei.time_tracker.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeListScreen(
    modifier: Modifier = Modifier,
    viewModel: TimeListViewModel = koinViewModel(),
    onNavigateToSettings: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.currentDate.format(DateTimeFormatter.ofPattern("EEE dd.MM.yyyy")),
                        fontWeight = FontWeight.Medium
                    )
                },
                actions = {
                    CalendarButtonWithPopup(
                        currentDate = uiState.currentDate,
                        onDateSelected = { date ->
                            viewModel.changeDate(date)
                        }
                    )
                    IconButton( onClick = onNavigateToSettings ) {
                        Icon(
                            imageVector = Icons.Rounded.Settings,
                            contentDescription = "Go to settings",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        bottomBar = {
            TimeEntryTimer(
                entry = uiState.activeTimeEntry,
                saveTimeEntry = viewModel::saveTimeEntry,
                hiddenCategories = uiState.hiddenCategories,
                draftName = uiState.draftName,
                draftCategory = uiState.draftCategory
            )
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {

            if (uiState.timeEntries.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No time entries added yet")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                ) {
                    items(
                        items = uiState.timeEntries,
                        key = { timeEntry -> timeEntry.id!! }
                    ) { timeEntry ->
                        SwipeableWithActions(
                            content = {
                                TimeEntryCard(
                                    entry = timeEntry,
                                    setDraft = viewModel::setDraft
                                )
                            },
                            actions = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .clip(MaterialTheme.shapes.medium)
                                        .background(MaterialTheme.colorScheme.error)
                                        .padding(horizontal = MaterialTheme.spacing.medium)
                                        .clickable { viewModel.deleteTimeEntry(timeEntry) },
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