package ru.graphorismo.simplealarm.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.graphorismo.simplealarm.R
import ru.graphorismo.simplealarm.domain.Alarm
import ru.graphorismo.simplealarm.domain.MainUiEvent

class AlarmsListRecyclerAdapter(val mainViewModel: MainViewModel) :
    RecyclerView.Adapter<AlarmsListRecyclerAdapter.ViewHolder>() {

    var data = mutableListOf<Alarm>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var dateTextView: TextView
        var timeTextView: TextView
        var buttonRemove: ImageButton

        init {
            dateTextView = itemView.findViewById(R.id.alarmListItem_textView_dateText)
            timeTextView = itemView.findViewById(R.id.alarmListItem_textView_timeText)
            buttonRemove = itemView.findViewById(R.id.alarmListItem_imageButton_deleteButton)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.alarms_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alarmData = data[position]
        var date = "DATE: ${alarmData.day}.${alarmData.month}.${alarmData.year}"
        holder.dateTextView.text = date
        var time = "TIME ${alarmData.hour}:${alarmData.minute}"
        holder.timeTextView.text = time
        holder.buttonRemove.setOnClickListener {
            mainViewModel.onMainUiEvent(MainUiEvent.AlarmRemove(position))
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}