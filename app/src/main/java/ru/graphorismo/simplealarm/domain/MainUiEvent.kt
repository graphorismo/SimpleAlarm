package ru.graphorismo.simplealarm.domain

sealed interface MainUiEvent{
    object MainUiLoad: MainUiEvent
    data class NewAlarmAdd(val alarm: Alarm): MainUiEvent
    data class AlarmRemove(val index: Int): MainUiEvent
}