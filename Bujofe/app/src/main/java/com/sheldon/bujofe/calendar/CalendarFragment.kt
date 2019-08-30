package com.sheldon.bujofe.calendar


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sheldon.bujofe.R
import com.sheldon.bujofe.databinding.FragmentCalendarBinding
import android.util.Log
import com.roundtableapps.timelinedayviewlibrary.Event
import com.roundtableapps.timelinedayviewlibrary.EventView

class CalendarFragment : Fragment() {

    private lateinit var viewModel: CalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentCalendarBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val msg = "Selected date is $year/${month + 1}/$dayOfMonth"
            binding.textView2.text = msg
        }


        val fromValue = 1567126986832
        val toValue = System.currentTimeMillis()

        val myEvent = Event()
            myEvent.startTime = fromValue.toFloat()
            myEvent.endTime = toValue.toFloat()

        var myEventView = EventView(this.context,myEvent,
            setupView = {myView->
                myView.findViewById<TextView>(R.id.tvTitle).text = "品祥要健身囉"
            }
        )

        binding.timeLine.addEvent(myEventView)


        val timeStamp = System.currentTimeMillis()
        Log.d("xxxxx", timeStamp.toString())


        return binding.root
    }
}
