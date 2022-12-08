package ru.graphorismo.simplealarm.data

import kotlinx.coroutines.flow.Flow
import ru.graphorismo.simplealarm.domain.Alarm
import java.util.UUID
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(private val alarmDAO: AlarmDAO) :
    IAlarmRepository {

    override suspend fun getAlarmsFromRoom(): Flow<List<Alarm>> {
        return alarmDAO.getAlarms()
    }

    override suspend fun getAlarmFromRoom(id: UUID): Flow<Alarm> {
        return alarmDAO.getAlarm(id)
    }

    override suspend fun addAlarmToRoom(alarm: Alarm) {
        alarmDAO.addAlarm(alarm)
    }

    override suspend fun deleteAlarmFromRoom(alarm: Alarm) {
        alarmDAO.deleteAlarm(alarm)
    }

}