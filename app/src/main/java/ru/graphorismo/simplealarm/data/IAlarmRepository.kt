package ru.graphorismo.simplealarm.data

import kotlinx.coroutines.flow.Flow
import ru.graphorismo.simplealarm.domain.Alarm
import java.util.UUID

interface IAlarmRepository{
    suspend fun getAlarmsFromRoom(): Flow<List<Alarm>>

    suspend fun getAlarmFromRoom(id: UUID): Flow<Alarm>

    suspend fun addAlarmToRoom(alarm: Alarm)

    suspend fun deleteAlarmFromRoom(alarm: Alarm)

}