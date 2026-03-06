package me.narei.time_tracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.narei.time_tracker.data.TimeEntry
import me.narei.time_tracker.data.category.Category
import me.narei.time_tracker.ui.components.shared.CategoryIcon
import me.narei.time_tracker.ui.theme.spacing
import me.narei.time_tracker.util.formatDurationString
import me.narei.time_tracker.util.toLocalDateTime
import java.time.Duration
import java.time.format.DateTimeFormatter

@Composable
fun TimeEntryCard(
    modifier: Modifier = Modifier,
    entry: TimeEntry,
    setDraft: (String, Category) -> Unit
) {

    val hourFormatter = remember { DateTimeFormatter.ofPattern("HH:mm") }

    val startTimeDate = entry.startTime.toLocalDateTime()

    requireNotNull(entry.endTime) { "End time of entry of id ${entry.id} is null." }
    val endTimeDate = entry.endTime.toLocalDateTime()

    Row (
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { setDraft(entry.name, entry.category) }
                )
            }
            .background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.medium)
            .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.medium)
            .padding(MaterialTheme.spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {

        CategoryIcon(category = entry.category)

        Column(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = entry.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "${startTimeDate.format(hourFormatter)} - ${endTimeDate.format(hourFormatter)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = formatDurationString(Duration.between(startTimeDate, endTimeDate).seconds),
            style = MaterialTheme.typography.titleMedium
        )
    }

}
