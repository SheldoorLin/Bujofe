package com.sheldon.bujofe.calendar


import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
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
import com.sheldon.bujofe.R
import com.sheldon.bujofe.databinding.FragmentCalendarBinding
import kotlinx.android.synthetic.main.calendar_day_legend.view.*
import kotlinx.android.synthetic.main.item_calendar_day.view.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter


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

        return binding.root
    }

    private var selectedDate: LocalDate? = null

    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")

    private val calendarAdapter = CalendarAdapter()

    private val flights = generateFlights().groupBy { it.time.toLocalDate() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventRecycler.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        eventRecycler.adapter = calendarAdapter
        eventRecycler.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )
        calendarAdapter.notifyDataSetChanged()

        val daysOfWeek = daysOfWeekFromLocale()

        val currentMonth = YearMonth.now()

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
            val flightTopView = view.exFiveDayFlightTop
            val flightBottomView = view.exFiveDayFlightBottom

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        if (selectedDate != day.date) {
                            val oldDate = selectedDate
                            selectedDate = day.date
                            eventCalendar.notifyDateChanged(day.date)
                            oldDate?.let { eventCalendar.notifyDateChanged(it) }
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


                val flightTopView = container.flightTopView

                val flightBottomView = container.flightBottomView

                flightTopView.background = null

                flightBottomView.background = null

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.setTextColorRes(R.color.black)
                    layout.setBackgroundResource(if (selectedDate == day.date) R.drawable.calendar_selected_bg else 0)

                    val flights = flights[day.date]

                    if (flights != null) {

                        if (flights.count() == 1) {
                            flightBottomView.setBackgroundColor(view.context.getColorCompat(flights[0].color))
                        } else {
                            flightTopView.setBackgroundColor(view.context.getColorCompat(flights[0].color))
                            flightBottomView.setBackgroundColor(view.context.getColorCompat(flights[1].color))
                        }
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

        eventCalendar.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                // Setup each header day text if we have not done that already.
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    container.legendLayout.children.map { it as TextView }
                        .forEachIndexed { index, tv ->
                            tv.text = daysOfWeek[index].name.take(3)
                            tv.setTextColorRes(R.color.color_brown_FF8A6548)
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                        }
                    month.yearMonth
                }
            }
        }

        eventCalendar.monthScrollListener = { month ->
            val title = "${monthTitleFormatter.format(month.yearMonth)} ${month.yearMonth.year}"
            exFiveMonthYearText.text = title

            selectedDate?.let {
                // Clear selection if we scroll to a new month.
                selectedDate = null
                eventCalendar.notifyDateChanged(it)
                updateAdapterForDate(null)
            }
        }

        exFiveNextMonthImage.setOnClickListener {
            eventCalendar.findFirstVisibleMonth()?.let {
                eventCalendar.smoothScrollToMonth(it.yearMonth.next)
            }
        }

        exFivePreviousMonthImage.setOnClickListener {
            eventCalendar.findFirstVisibleMonth()?.let {
                eventCalendar.smoothScrollToMonth(it.yearMonth.previous)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        requireActivity().window.statusBarColor =
            requireContext().getColorCompat(R.color.example_5_toolbar_color)
    }

    override fun onStop() {
        super.onStop()
        requireActivity().window.statusBarColor =
            requireContext().getColorCompat(R.color.colorPrimaryDark)
    }

    private fun updateAdapterForDate(date: LocalDate?) {
        calendarAdapter.flights.clear()
        calendarAdapter.flights.addAll(flights[date].orEmpty())
        calendarAdapter.notifyDataSetChanged()
    }

}
