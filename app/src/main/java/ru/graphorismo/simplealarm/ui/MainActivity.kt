package ru.graphorismo.simplealarm.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.graphorismo.simplealarm.R
import ru.graphorismo.simplealarm.domain.Alarm
import ru.graphorismo.simplealarm.domain.MainUiEvent
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener
{

    val viewModel: MainViewModel by viewModels()

    lateinit var addAlarmButton: Button

    lateinit var alarmsListRecyclerView: RecyclerView
    lateinit var alarmsListRecyclerAdapter: AlarmsListRecyclerAdapter

    lateinit var settedAlarm: Alarm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setDefaultSettedAlarm()

        viewModel.onMainUiEvent(MainUiEvent.MainUiLoad)

        addAlarmButton = findViewById(R.id.activityMain_button_addAlarm)
        alarmsListRecyclerView = findViewById(R.id.activityMain_recyclerView_alarmsList)

        addAlarmButton.setOnClickListener {
            showTimePicker()
            showDatePicker()
            //new alarm event trigger on the datePicker close
        }

        alarmsListRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        alarmsListRecyclerAdapter = AlarmsListRecyclerAdapter(viewModel)
        alarmsListRecyclerView.adapter = alarmsListRecyclerAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainUiStateFlow.collect { uiState ->
                    alarmsListRecyclerAdapter.data = uiState.alarmsList.toMutableList()
                    alarmsListRecyclerAdapter.notifyDataSetChanged()
                }
            }
        }



    }

    private fun setDefaultSettedAlarm(){
        val calendar = Calendar.getInstance()
        settedAlarm =
            Alarm(year = calendar.get(Calendar.YEAR),
                month = calendar.get(Calendar.MONTH),
                day = calendar.get(Calendar.DAY_OF_MONTH),
                hour = calendar.get(Calendar.HOUR_OF_DAY),
                minute = calendar.get(Calendar.MINUTE)
            )
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

        viewModel.onMainUiEvent(MainUiEvent.NewAlarmAdd(settedAlarm))

    }
}
