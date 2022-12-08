package ru.graphorismo.simplealarm.domain

import android.icu.util.Calendar
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Alarm(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int){

}
