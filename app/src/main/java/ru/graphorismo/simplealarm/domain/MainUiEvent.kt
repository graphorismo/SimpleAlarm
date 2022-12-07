package ru.graphorismo.simplealarm.domain

sealed interface MainUiEvent{
    data class NewAlarmAdd(val alarm: Alarm): MainUiEvent
}