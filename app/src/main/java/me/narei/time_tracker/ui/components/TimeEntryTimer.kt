package me.narei.time_tracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Stop
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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import me.narei.time_tracker.data.TimeEntry
import me.narei.time_tracker.ui.theme.spacing

@Composable
fun TimeEntryTimer(
    modifier: Modifier = Modifier,
    entry: TimeEntry? = null,
    saveTimeEntry: (TimeEntry) -> Unit
) {

    val isRunning = entry !== null

    var value by remember(entry?.id) { mutableStateOf(entry?.name ?: "") }

    var currentDisplayTime by remember(entry?.id) {
        mutableLongStateOf( if (entry != null)  System.currentTimeMillis() / 1000 - entry.startTime else 0L )
    }

    LaunchedEffect(isRunning, entry?.startTime) {
        if (isRunning) {
            while (true) {
                currentDisplayTime = System.currentTimeMillis() / 1000 - entry.startTime
                delay(200L)
            }
        }
    }

    LaunchedEffect(value) {
        if (isRunning && value != entry.name) {
            delay(1000L)
            saveTimeEntry(entry.copy(name = value))
        }
    }

    Box(
        modifier = modifier.fillMaxWidth().navigationBarsPadding().imePadding().padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(12.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                .padding(MaterialTheme.spacing.small),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color(0xFF2a9d8f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Category icon",
                        tint = Color.White
                    )
                }
                BasicTextField(
                    value = value,
                    onValueChange = { value = it },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    decorationBox = { innerTextField ->
                        Box(contentAlignment = Alignment.CenterStart) {
                            if (value.isEmpty()) {
                                Text(
                                    text = "Write name...",
                                    color = Color.LightGray,
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text("$currentDisplayTime")

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Magenta)
                        .clickable {
                            if (isRunning) {
                                saveTimeEntry(entry.copy(
                                    name = value,
                                    endTime = System.currentTimeMillis() / 1000
                                ))
                            } else {
                                saveTimeEntry(TimeEntry(
                                    name = value,
                                    startTime = System.currentTimeMillis() / 1000
                                ))
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isRunning) Icons.Rounded.Stop else Icons.Rounded.PlayArrow,
                        contentDescription = if (isRunning) "Stop timer" else "Start timer"
                    )
                }
            }
        }
    }

}