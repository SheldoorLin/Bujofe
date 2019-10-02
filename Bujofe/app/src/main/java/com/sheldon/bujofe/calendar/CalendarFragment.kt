package com.sheldon.bujofe.calendar


import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import com.sheldon.bujofe.MainActivity
import com.sheldon.bujofe.R
import com.sheldon.bujofe.databinding.FragmentCalendarBinding
import kotlinx.android.synthetic.main.item_calendar_day_legend.view.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.item_calendar_day.view.*
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter


class CalendarFragment : Fragment() {

    private val viewModel: CalendarViewModel by lazy {
        ViewModelProviders.of(this).get(CalendarViewModel::class.java)
    }

    private var selectedDate: LocalDate? = null

    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")

    private val calendarAdapter = CalendarAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentCalendarBinding.inflate(inflater, container, false)
        (activity as MainActivity).binding.toolbar.visibility = View.VISIBLE
        (activity as MainActivity).binding.imgLogInResult.setImageResource(R.color.color_orange_text_gray)
        binding.lifecycleOwner = this


        binding.eventRecycler.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.eventRecycler.adapter = calendarAdapter




        viewModel.teachLists.observe(this, Observer {
            it.let {
                viewModel.getTeacherList()
                Log.d("CalendarView", "getTeacherList() = ${viewModel.getTeacherList()}")
                val classMutes = viewModel.getTeacherList().groupBy { it.time.toLocalDate() }
                viewModel.classMutes = classMutes
                binding.eventCalendar.notifyCalendarChanged()
                Log.d("CalendarView", "classMutes = $classMutes")
            }
        })
        calendarAdapter.notifyDataSetChanged()



//        Log.d("CalendarView", "getTeacherList() = ${viewModel.getTeacherList().toString()}")
        val daysOfWeek = daysOfWeekFromLocale()

        val currentMonth = YearMonth.now()

        val eventCalendar = binding.eventCalendar
        eventCalendar.setup(
            currentMonth.minusMonths(10),
            currentMonth.plusMonths(10),
            daysOfWeek.first()
        )
        eventCalendar.scrollToMonth(currentMonth)

        class DayViewContainer(view: View) : ViewContainer(view) {

            lateinit var day: CalendarDay // Will be set when this container is bound.

            val textView = view.exFiveDayText

            val layout = view.exFiveDayLayout

            val dayTopView = view.exFiveDayFlightTop

            val dayBottomView = view.exFiveDayFlightBottom

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        if (selectedDate != day.date) {
                            val oldDate = selectedDate
                            selectedDate = day.date
                            eventCalendar.notifyDateChanged(day.date)
                            oldDate?.let {
                                eventCalendar.notifyDateChanged(it)
                            }
                            updateAdapterForDate(day.date)
                        }
                    }
                }
            }
        }

        eventCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {

                container.day = day

                val textView = container.textView

                val layout = container.layout

                textView.text = day.date.dayOfMonth.toString()

                val dayTopView = container.dayTopView

                val dayBottomView = container.dayBottomView

                dayTopView.background = null

                dayBottomView.background = null

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.setTextColorRes(R.color.black)
                    layout.setBackgroundResource(
                        if (selectedDate == day.date) {
                            R.drawable.calendar_selected_bg
                        } else {
                            R.drawable.calendar_unselected_bg
                        }
                    )

                    val classMutes = viewModel.classMutes[day.date]

                    if (classMutes != null) {
                        dayBottomView.setBackgroundResource(R.drawable.calendar_event_dot_shape)
                    }


                } else {
                    textView.setTextColorRes(R.color.title_color_white)
                    layout.background = null
                }
            }

        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayout = view.legendLayout
        }

        //一周星期標題
        eventCalendar.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                // Setup each header day text if we have not done that already.
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    container.legendLayout.children.map {
                        it as TextView
                    }.forEachIndexed { index, tv ->
                        tv.text = daysOfWeek[index].name.take(3)
                        tv.setTextColorRes(R.color.color_orange_Dark)
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                    }
                    month.yearMonth
                }
            }
        }

        eventCalendar.monthScrollListener = { month ->
            val title = "${month.yearMonth.year}年 ${monthTitleFormatter.format(month.yearMonth)} "
            exFiveMonthYearText.text = title

            selectedDate?.let {
                // Clear selection if we scroll to a new month.
                selectedDate = null
                eventCalendar.notifyDateChanged(it)
                updateAdapterForDate(null)
            }
        }

        binding.exFiveNextMonthImage.setOnClickListener {
            eventCalendar.findFirstVisibleMonth()?.let {
                eventCalendar.smoothScrollToMonth(it.yearMonth.next)
            }
        }

        binding.exFivePreviousMonthImage.setOnClickListener {
            eventCalendar.findFirstVisibleMonth()?.let {
                eventCalendar.smoothScrollToMonth(it.yearMonth.previous)
            }
        }

        binding.exFiveMonthYearText.setOnClickListener {
            if (eventCalendar.visibility == View.VISIBLE) {
                binding.eventCalendar.visibility = View.GONE
            } else {
                binding.eventCalendar.visibility = View.VISIBLE
            }
        }


        return binding.root
    }


    private fun updateAdapterForDate(date: LocalDate?) {
        calendarAdapter.classMute.clear()
        calendarAdapter.classMute.addAll(viewModel.classMutes[date].orEmpty())
        calendarAdapter.notifyDataSetChanged()
    }
}
