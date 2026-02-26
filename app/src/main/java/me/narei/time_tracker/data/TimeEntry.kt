package me.narei.time_tracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimeEntry(

    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    @ColumnInfo(name = "start_time") val startTime: Long,
    @ColumnInfo(name = "end_time") val endTime: Long? = null,

    )
