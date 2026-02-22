package me.narei.time_tracker.data

data class TimeEntry(

    val startTime: Long,
    val endTime: Long?,
    val taskName: String,
    val colorHex: String

)
