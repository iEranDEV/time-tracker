package me.narei.time_tracker.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun Long.toLocalDateTime(): LocalDateTime {
    return Instant.ofEpochSecond(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
}

fun formatDurationString(seconds: Long): String = String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60)
