package ru.graphorismo.simplealarm.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.graphorismo.simplealarm.data.IAlarmRepository
import ru.graphorismo.simplealarm.data.AlarmRepositoryImpl
import ru.graphorismo.simplealarm.domain.MainUiEvent
import ru.graphorismo.simplealarm.domain.MainUiState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val alarmRepository: IAlarmRepository):
    ViewModel() {

    var mainUiStateFlow = MutableStateFlow(MainUiState(mutableListOf()))

    fun onMainUiEvent(mainUiEvent: MainUiEvent){
        when(mainUiEvent){
            is MainUiEvent.MainUiLoad ->{
                viewModelScope.launch(Dispatchers.IO) {
                    val flow = alarmRepository.getAlarmsFromRoom()
                    flow.collect {
                        mainUiStateFlow.value = MainUiState(it)
                    }
                }
            }
            is MainUiEvent.NewAlarmAdd -> {
                val oldAlarmsList = mainUiStateFlow.value.alarmsList
                val newAlarmsList = oldAlarmsList.toMutableList()
                newAlarmsList.add(mainUiEvent.alarm)
                viewModelScope.launch(Dispatchers.IO) {
                    alarmRepository.addAlarmToRoom(mainUiEvent.alarm)
                }
                mainUiStateFlow.value = MainUiState(newAlarmsList.toList())
                Log.d("MAIN_UI_EVENT","Handle new alarm event. Current size of alarm lists: ${newAlarmsList.size}")
            }
            is MainUiEvent.AlarmRemove -> {
                val oldAlarmsList = mainUiStateFlow.value.alarmsList
                val newAlarmsList = oldAlarmsList.toMutableList()
                viewModelScope.launch(Dispatchers.IO) {
                    alarmRepository.deleteAlarmFromRoom(oldAlarmsList[mainUiEvent.index])
                }
                newAlarmsList.removeAt(mainUiEvent.index)
                mainUiStateFlow.value = MainUiState(newAlarmsList.toList())
                Log.d("MAIN_UI_EVENT","Handle remove alarm event. Current size of alarm lists: ${newAlarmsList.size}")
            }
        }
    }
}