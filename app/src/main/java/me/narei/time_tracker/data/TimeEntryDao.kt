package me.narei.time_tracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZoneId

@Dao
abstract class TimeEntryDao {

    @Upsert
    abstract suspend fun upsertTimeEntry(timeEntry: TimeEntry)

    @Delete
    abstract suspend fun deleteTimeEntry(timeEntry: TimeEntry)

    @Query("SELECT * FROM TimeEntry WHERE end_time IS NULL LIMIT 1")
    abstract fun getActiveTimeEntry(): Flow<TimeEntry?>

    @Query("SELECT * FROM TimeEntry WHERE start_time >= :startOfDay AND start_time <= :endOfDay AND end_time IS NOT NULL")
    protected abstract fun getEntriesInRange(startOfDay: Long, endOfDay: Long): Flow<List<TimeEntry>>

    fun getEntriesForDate(date: LocalDate): Flow<List<TimeEntry>> {
        val zoneId = ZoneId.systemDefault()

        val startOfDay = date.atStartOfDay(zoneId).toEpochSecond()
        val endOfDay = date.plusDays(1).atStartOfDay(zoneId).toEpochSecond() - 1

        return getEntriesInRange(startOfDay, endOfDay)
    }

}