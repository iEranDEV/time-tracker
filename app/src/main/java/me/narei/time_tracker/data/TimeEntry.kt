package me.narei.time_tracker.data

data class TimeEntry(

    val name: String,
    val startTime: Long,
    val endTime: Long? = null,

) {

    fun getDurationText(): String {
        if (endTime == null) return ""

        val diff = endTime - startTime
        val hours = diff / 3600
        val minutes = (diff % 3600) / 60

        return "${hours}:${minutes.toString().padStart(2, '0')}h"
    }

}
