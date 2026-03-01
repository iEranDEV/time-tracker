package me.narei.time_tracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import me.narei.time_tracker.data.TimeEntry
import me.narei.time_tracker.data.category.Category
import me.narei.time_tracker.ui.components.shared.CategoryIcon
import me.narei.time_tracker.ui.theme.spacing
import me.narei.time_tracker.util.formatDurationString
import kotlin.math.exp

@Composable
fun TimeEntryTimer(
    modifier: Modifier = Modifier,
    entry: TimeEntry? = null,
    saveTimeEntry: (TimeEntry) -> Unit,
    hiddenCategories: Set<Category>? = null
) {

    val isRunning = entry !== null

    var expanded by remember { mutableStateOf(false) }

    var nameValue by remember(entry?.id) { mutableStateOf(entry?.name ?: "") }
    var categoryValue by remember(entry?.id) { mutableStateOf(entry?.category ?: run {
        if (hiddenCategories != null && hiddenCategories.contains(Category.OTHER)) {
            Category.entries.filterNot { it in hiddenCategories }.randomOrNull() ?: Category.OTHER
        } else {
            Category.OTHER
        }
    }) }

    var elapsedSeconds by remember(entry?.id) {
        mutableLongStateOf( if (entry != null)  System.currentTimeMillis() / 1000 - entry.startTime else 0L )
    }

    LaunchedEffect(isRunning, entry?.startTime) {
        if (isRunning) {
            while (true) {
                elapsedSeconds = System.currentTimeMillis() / 1000 - entry.startTime
                delay(200L)
            }
        }
    }

    LaunchedEffect(hiddenCategories) {
        if (hiddenCategories != null && hiddenCategories.contains(categoryValue)) {
            categoryValue = Category.entries.filterNot { it in hiddenCategories }.randomOrNull() ?: Category.OTHER
        }
    }

    LaunchedEffect(nameValue, categoryValue) {
        if (isRunning && (nameValue != entry.name || categoryValue != entry.category)) {
            delay(1000L)
            saveTimeEntry(entry.copy(name = nameValue, category = categoryValue))
        }
    }

    Box(
        modifier = modifier.fillMaxWidth().navigationBarsPadding().imePadding().padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.medium)
                .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.medium)
                .padding(MaterialTheme.spacing.small),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                Box {
                    Box(modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .clickable {
                            if (Category.entries.size - (hiddenCategories?.size ?: 0) > 1) expanded = true
                        }
                    ) {
                        CategoryIcon(category = categoryValue)
                    }

                    if (hiddenCategories != null) {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            offset = DpOffset(x = 0.dp, y = -MaterialTheme.spacing.small),
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier
                                .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.medium)
                                .width(250.dp),
                            containerColor = MaterialTheme.colorScheme.surface,
                        ) {
                            Category.entries.forEach { category ->
                                if (!hiddenCategories.contains(category)) {
                                    DropdownMenuItem(
                                        text = { Text(category.displayName) },
                                        leadingIcon = { CategoryIcon(category = category) },
                                        onClick = {
                                            categoryValue = category
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                BasicTextField(
                    value = nameValue,
                    onValueChange = { nameValue = it },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f), MaterialTheme.shapes.small)
                        .padding(horizontal = MaterialTheme.spacing.medium, vertical = MaterialTheme.spacing.small),
                    decorationBox = { innerTextField ->
                        Box(contentAlignment = Alignment.CenterStart) {
                            if (nameValue.isEmpty()) {
                                Text(
                                    text = "I am working on...",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large, Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatDurationString(elapsedSeconds),
                    style = MaterialTheme.typography.bodyLarge
                )

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable {
                            if (isRunning) {
                                saveTimeEntry(entry.copy(
                                    name = nameValue,
                                    category = categoryValue,
                                    endTime = System.currentTimeMillis() / 1000
                                ))
                            } else {
                                saveTimeEntry(TimeEntry(
                                    name = nameValue,
                                    category = categoryValue,
                                    startTime = System.currentTimeMillis() / 1000
                                ))
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isRunning) Icons.Rounded.Stop else Icons.Rounded.PlayArrow,
                        contentDescription = if (isRunning) "Stop timer" else "Start timer",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }

}