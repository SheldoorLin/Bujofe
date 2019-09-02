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
import com.sheldon.bujofe.timelayout.Event
import com.sheldon.bujofe.timelayout.EventView


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


        val myEventView = EventView(
            context,
            Event().apply {
                startTime = 1F
                endTime = 3F
                title = "1"
//              Log.d("AAmyEvent1","${myEvent1.toString()}")
            },
            itemsMargin = 1, //optional
            layoutResourceId = R.layout.item_event_three, //optional
            setupView = { myView ->
                //SETUP VIEW
                myView.findViewById<TextView>(R.id.tvTitle).text = "品翔要健身囉"
                Log.d("Sheldon", "setupView1,I'am here")
            },
            onItemClick = { event ->
                //CLICK EVENT
                Log.d("Sheldon", "I'am here,onItemClick")
            }
        )
        binding.timeLine.addEvent(myEventView)

        val myEventView2 = EventView(context,
            Event().apply {
                startTime = 1F
                endTime = 6F
                title = "2"

            },//加入開始結束時間
            itemsMargin = 1, //與旁邊方塊的間隔
            layoutResourceId = R.layout.item_event, //使用甚麼顏色的背景
            setupView = { myView ->
                //SETUP VIEW
                myView.findViewById<TextView>(R.id.tvTitle).text = "我要健身囉"
                Log.d("Sheldon", "setupView2,I'am here")
            },
            onItemClick = { event ->
                //CLICK EVENT
                Log.d("Sheldon", "I'am here,onItemClick")
            }
        )
        binding.timeLine.addEvent(myEventView2)


        val myEventView3 = EventView(context,
            Event().apply {
                startTime = 2F
                endTime = 4F
                title = "2"

            },
            itemsMargin = 1, //optional
            layoutResourceId = R.layout.item_event, //optional
            setupView = { myView ->
                //SETUP VIEW
                myView.findViewById<TextView>(R.id.tvTitle).text = "吃飯時間"
                Log.d("Sheldon", "setupView2,I'am here")
            },
            onItemClick = { event ->
                //CLICK EVENT
                Log.d("Sheldon", "I'am here,onItemClick")
            }
        )
        binding.timeLine.addEvent(myEventView3)




        return binding.root
    }
}
