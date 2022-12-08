package ru.graphorismo.simplealarm.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.graphorismo.simplealarm.domain.Alarm
import java.util.UUID

@Dao
interface AlarmDAO {
    @Query("SELECT * FROM Alarm ORDER BY id ASC")
    fun getAlarms(): Flow<List<Alarm>>

    @Query("SELECT * FROM Alarm WHERE id = :id")
    fun getAlarm(id: UUID): Flow<Alarm>

    @Insert(onConflict = IGNORE)
    fun addAlarm(alarm: Alarm)

    @Delete
    fun deleteAlarm(alarm: Alarm)
}