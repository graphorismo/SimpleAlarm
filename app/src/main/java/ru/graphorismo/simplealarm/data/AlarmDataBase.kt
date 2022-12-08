package ru.graphorismo.simplealarm.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.graphorismo.simplealarm.domain.Alarm

@Database(entities = [ Alarm::class ], version=1)
abstract class AlarmDataBase : RoomDatabase() {
    abstract fun alarmDAO(): AlarmDAO
}