package ru.graphorismo.simplealarm.ui

import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import ru.graphorismo.simplealarm.R
import ru.graphorismo.simplealarm.domain.Alarm
import java.util.*


class MainActivity : AppCompatActivity(),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener
{

    lateinit var addAlarmButton: Button
    lateinit var settedAlarm: Alarm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendar = Calendar.getInstance()
        settedAlarm =
            Alarm(year = calendar.get(Calendar.YEAR),
                month = calendar.get(Calendar.MONTH),
                day = calendar.get(Calendar.DAY_OF_MONTH),
                hour = calendar.get(Calendar.HOUR_OF_DAY),
                minute = calendar.get(Calendar.MINUTE)
            )

        addAlarmButton = findViewById<Button>(R.id.activityMain_button_addAlarm)
        addAlarmButton.setOnClickListener {
            showTimePicker()
            showDatePicker()
        }
    }

    private fun showDatePicker(){
        val calendar = Calendar.getInstance()
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val datePickerDialog = DatePickerDialog(this, this, year, month, dayOfMonth)
        datePickerDialog.show()
    }

    private fun showTimePicker(){
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this, this, hourOfDay, minute, true)
        timePickerDialog.show()
    }



    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        settedAlarm = settedAlarm.copy(year = year, month = month, day = dayOfMonth)
        Log.d("DATE_PICK", "Set date y:${settedAlarm.year} m:${settedAlarm.month} d:${settedAlarm.day}")
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        settedAlarm = settedAlarm.copy(hour = hour, minute = minute)
        Log.d("TIME_PICK", "Set time h:${settedAlarm.hour} m:${settedAlarm.minute}")
    }
}
