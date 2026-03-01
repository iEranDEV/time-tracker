package me.narei.time_tracker.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarButtonWithPopup(
    currentDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    val initialMillis = remember(currentDate) {
        currentDate.atStartOfDay(ZoneId.of("UTC")).toInstant().toEpochMilli()
    }
    val datePickerState = rememberDatePickerState(initialMillis)

    IconButton(
        onClick = { showDatePicker = true }
    ) {
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = "Open date selector",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        datePickerState.selectedDateMillis?.let { millis ->
                            onDateSelected(Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate())
                        }
                    }
                ) {
                    Text("Confirm")
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    dayContentColor = MaterialTheme.colorScheme.onSurface,
                    weekdayContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    headlineContentColor = MaterialTheme.colorScheme.onSurface,
                    selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,
                    selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                    todayContentColor = MaterialTheme.colorScheme.primary,
                    todayDateBorderColor = MaterialTheme.colorScheme.secondary,
                    navigationContentColor = MaterialTheme.colorScheme.onSurface,

                )
            )
        }
    }
}