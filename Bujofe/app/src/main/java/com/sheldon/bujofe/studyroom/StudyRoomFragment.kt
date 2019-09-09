package com.sheldon.bujofe.studyroom

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import com.sheldon.bujofe.BujofeApplication
import com.sheldon.bujofe.R
import com.sheldon.bujofe.calendar.*
import com.sheldon.bujofe.databinding.FragmentStudyRoomBinding
import kotlinx.android.synthetic.main.calendar_day_legend.view.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.item_calendar_day.view.*
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter

class StudyRoomFragment : Fragment() {

    private val viewModel: StudyRoomViewModel by lazy {
        ViewModelProviders.of(this).get(StudyRoomViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            FragmentStudyRoomBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        binding.spinnerWeekofday.adapter = WeekSpinnerAdapter(
            BujofeApplication.instance.resources.getStringArray(R.array.week)
        )


        val seatTable = binding.seatView
        seatTable.setData(5, 9)
        seatTable.setScreenName("黑板")
        seatTable.setMaxSelected(1)
        seatTable.setSeatChecker(object : SeatTable.SeatChecker {

            override fun isValidSeat(row: Int, column: Int): Boolean {
//                    while (column ==1) {
//                        return false
//                    }
                return true
            }

            override fun isSold(row: Int, column: Int): Boolean {

                return row == 2 && column == 1
            }

            override fun checked(row: Int, column: Int) {

            }

            override fun unCheck(row: Int, column: Int) {

            }

            override fun checkedSeatTxt(row: Int, column: Int): Array<String>? {
                return null
            }

        })


//        binding.spinnerWeekofday.onItemSelectedListener =
//            object : AdapterView.OnItemSelectedListener {
//                override fun onNothingSelected(p0: AdapterView<*>?) {
//                    //To change body of created functions use File | Settings | File Templates.
//                }
//
//                override fun onItemSelected(
//                    p0: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    p3: Long
//                ) {
//                    when (position) {
//                        0 -> seatTable.setSeatChecker(object : SeatTable.SeatChecker {
//
//                            override fun isValidSeat(row: Int, column: Int): Boolean {
////                    while (column ==1) {
////                        return false
////                    }
//                                return true
//                            }
//
//                            override fun isSold(row: Int, column: Int): Boolean {
//
//                                return row == 2 && column == 1
//                            }
//
//                            override fun checked(row: Int, column: Int) {
//
//                            }
//
//                            override fun unCheck(row: Int, column: Int) {
//
//                            }
//
//                            override fun checkedSeatTxt(row: Int, column: Int): Array<String>? {
//                                return null
//                            }
//
//                        })
//                        1 -> seatTable.setSeatChecker(object : SeatTable.SeatChecker {
//
//                            override fun isValidSeat(row: Int, column: Int): Boolean {
////                    while (column ==1) {
////                        return false
////                    }
//                                return true
//                            }
//
//                            override fun isSold(row: Int, column: Int): Boolean {
//
//                                return row == 3 && column == 3
//                            }
//
//                            override fun checked(row: Int, column: Int) {
//
//                            }
//
//                            override fun unCheck(row: Int, column: Int) {
//
//                            }
//
//                            override fun checkedSeatTxt(row: Int, column: Int): Array<String>? {
//                                return null
//                            }
//
//                        })
//                        2 -> seatTable.setSeatChecker(object : SeatTable.SeatChecker {
//
//                            override fun isValidSeat(row: Int, column: Int): Boolean {
////                    while (column ==1) {
////                        return false
////                    }
//                                return true
//                            }
//
//                            override fun isSold(row: Int, column: Int): Boolean {
//
//                                return row == 4 && column == 2
//                            }
//
//                            override fun checked(row: Int, column: Int) {
//
//                            }
//
//                            override fun unCheck(row: Int, column: Int) {
//
//                            }
//
//                            override fun checkedSeatTxt(row: Int, column: Int): Array<String>? {
//                                return null
//                            }
//
//                        })
//                        3 -> seatTable.setSeatChecker(object : SeatTable.SeatChecker {
//
//                            override fun isValidSeat(row: Int, column: Int): Boolean {
////                    while (column ==1) {
////                        return false
////                    }
//                                return true
//                            }
//
//                            override fun isSold(row: Int, column: Int): Boolean {
//
//                                return row == 5 && column == 5
//                            }
//
//                            override fun checked(row: Int, column: Int) {
//
//                            }
//
//                            override fun unCheck(row: Int, column: Int) {
//
//                            }
//
//                            override fun checkedSeatTxt(row: Int, column: Int): Array<String>? {
//                                return null
//                            }
//
//                        })
//                        4 -> seatTable.setSeatChecker(object : SeatTable.SeatChecker {
//
//                            override fun isValidSeat(row: Int, column: Int): Boolean {
////                    while (column ==1) {
////                        return false
////                    }
//                                return true
//                            }
//
//                            override fun isSold(row: Int, column: Int): Boolean {
//
//                                return row == 0 && column == 5
//                            }
//
//                            override fun checked(row: Int, column: Int) {
//
//                            }
//
//                            override fun unCheck(row: Int, column: Int) {
//
//                            }
//
//                            override fun checkedSeatTxt(row: Int, column: Int): Array<String>? {
//                                return null
//                            }
//
//                        })
//                        5 -> seatTable.setSeatChecker(object : SeatTable.SeatChecker {
//
//                            override fun isValidSeat(row: Int, column: Int): Boolean {
////                    while (column ==1) {
////                        return false
////                    }
//                                return true
//                            }
//
//                            override fun isSold(row: Int, column: Int): Boolean {
//
//                                return row == 4 && column == 6
//                            }
//
//                            override fun checked(row: Int, column: Int) {
//
//                            }
//
//                            override fun unCheck(row: Int, column: Int) {
//
//                            }
//
//                            override fun checkedSeatTxt(row: Int, column: Int): Array<String>? {
//                                return null
//                            }
//
//                        })
//                        6 -> seatTable.setSeatChecker(object : SeatTable.SeatChecker {
//
//                            override fun isValidSeat(row: Int, column: Int): Boolean {
////                    while (column ==1) {
////                        return false
////                    }
//                                return true
//                            }
//
//                            override fun isSold(row: Int, column: Int): Boolean {
//
//                                return row == 1 && column == 6
//                            }
//
//                            override fun checked(row: Int, column: Int) {
//
//                            }
//
//                            override fun unCheck(row: Int, column: Int) {
//
//                            }
//
//                            override fun checkedSeatTxt(row: Int, column: Int): Array<String>? {
//                                return null
//                            }
//
//                        })
//                    }
//                }
//            }


//        /**
//         *mock data
//         *  */
//        val test_data_2: ArrayList<Seat> = ArrayList()
//        test_data_2.add(Seat("A01", "王小翔","ef5350"))
//        test_data_2.add(Seat("A02", "王大翔","ef5350"))
//        test_data_2.add(Seat("A03", "王中翔","ef5350"))
//        test_data_2.add(Seat("A04", "Eric","ef5350"))
//        test_data_2.add(Seat("A05", "Sandra","ef5350"))
//        test_data_2.add(Seat("A06", "Sophie","ef5350"))
//        test_data_2.add(Seat("A07", "Sheldon","ef5350"))
//        test_data_2.add(Seat("A08", "Terry","ef5350"))
//        test_data_2.add(Seat("A09", "Eltin","ef5350"))
//        test_data_2.add(Seat("A10", "Wayne","ef5350"))
//        val test_data_3: ArrayList<Seat> = ArrayList()
//        test_data_3.add(Seat("A01", "王小翔","ef5350"))
//        test_data_3.add(Seat("A02", "王大翔","ef5350"))
//        test_data_3.add(Seat("A03", "待預約","00c853"))
//        test_data_3.add(Seat("A04", "Eric","ef5350"))
//        test_data_3.add(Seat("A05", "Sandra","ef5350"))
//        test_data_3.add(Seat("A06", "Sophie","ef5350"))
//        test_data_3.add(Seat("A07", "待預約","00c853"))
//        test_data_3.add(Seat("A08", "Terry","ef5350"))
//        test_data_3.add(Seat("A09", "Eltin","ef5350"))
//        test_data_3.add(Seat("A10", "Wayne","ef5350"))
//
//
//
//
//
//
//
//
//        binding.seatRecycler.adapter = SeatAdapter()
//        binding.seatRecycler.layoutManager = GridLayoutManager(this.context,5,GridLayoutManager.HORIZONTAL,false)
//
//        binding.spinnerWeekofday.onItemSelectedListener =
//            object : AdapterView.OnItemSelectedListener {
//                override fun onNothingSelected(p0: AdapterView<*>?) {
//                    //To change body of created functions use File | Settings | File Templates.
//                }
//
//                override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
//                    when (position) {
//                        0 -> (binding.seatRecycler.adapter as SeatAdapter).submitList(test_data_3)
//                        1 -> (binding.seatRecycler.adapter as SeatAdapter).submitList(test_data_2)
//                        2 -> (binding.seatRecycler.adapter as SeatAdapter).submitList(test_data_2)
//                        3 -> (binding.seatRecycler.adapter as SeatAdapter).submitList(test_data_2)
//                        4 -> (binding.seatRecycler.adapter as SeatAdapter).submitList(test_data_2)
//                        5 -> (binding.seatRecycler.adapter as SeatAdapter).submitList(test_data_2)
//                        6 -> (binding.seatRecycler.adapter as SeatAdapter).submitList(test_data_2)
//                    }
//                }
//            }
//

        return binding.root
    }

    private var selectedDate: LocalDate? = null

    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")

    private val calendarAdapter = CalendarAdapter()

    private val flights = generateFlights().groupBy { it.time.toLocalDate() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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

    private fun updateAdapterForDate(date: LocalDate?) {
        calendarAdapter.flights.clear()
        calendarAdapter.flights.addAll(flights[date].orEmpty())
        calendarAdapter.notifyDataSetChanged()
    }
}
