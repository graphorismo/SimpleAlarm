package ru.graphorismo.simplealarm.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.graphorismo.simplealarm.domain.Alarm
import ru.graphorismo.simplealarm.domain.MainUiEvent
import ru.graphorismo.simplealarm.domain.MainUiState

class MainViewModel: ViewModel() {

    var mainUiStateFlow = MutableStateFlow(MainUiState(mutableListOf()))

    fun onMainUiEvent(mainUiEvent: MainUiEvent){
        when(mainUiEvent){
            is MainUiEvent.NewAlarmAdd -> {
                val oldAlarmsList = mainUiStateFlow.value.alarmsList
                val newAlarmsList = oldAlarmsList.toMutableList()
                newAlarmsList.add(mainUiEvent.alarm)
                mainUiStateFlow.value = MainUiState(newAlarmsList.toList())
                Log.d("MAIN_UI_EVENT","Handle new alarm event. Current size of alarm lists: ${newAlarmsList.size}")
            }
        }
    }
}