package com.sheldon.bujofe.calendar


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sheldon.bujofe.databinding.FragmentCalendarBinding
import androidx.lifecycle.ViewModelProviders


class CalendarFragment : Fragment() {

    private val viewModel: CalendarViewModel by lazy {
        ViewModelProviders.of(this).get(CalendarViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentCalendarBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val msg = "Selected date is $year/${month + 1}/$dayOfMonth"
            binding.textView2.text = msg

            /**
             *加入日歷事件
             */
            binding.timeLine.run {
                addEvent(viewModel.myEventView)
                addEvent(viewModel.myEventView2)
                addEvent(viewModel.myEventView3)
            }
        }

        return binding.root
    }
}
