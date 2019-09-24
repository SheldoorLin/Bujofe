package com.sheldon.bujofe.studyroom

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.sheldon.bujofe.MainActivity
import com.sheldon.bujofe.R
import com.sheldon.bujofe.calendar.getColorCompat
import com.sheldon.bujofe.databinding.FragmentStudyRoomBinding
import kotlinx.android.synthetic.main.item_studyroom_calendar_day.view.*
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter

class StudyRoomFragment : Fragment() {

    private val viewModel: StudyRoomViewModel by lazy {
        ViewModelProviders.of(this).get(StudyRoomViewModel::class.java)
    }
    private val TAG = "StudyRoomFragment"

    private var selectedDate: LocalDate? = null
    private val dateFormatter = DateTimeFormatter.ofPattern("dd")
    private val dayFormatter = DateTimeFormatter.ofPattern("EEE")
    private val monthFormatter = DateTimeFormatter.ofPattern("MMM")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            FragmentStudyRoomBinding.inflate(inflater, container, false)
        (activity as MainActivity).binding.toolbar.visibility = View.GONE
        (activity as MainActivity).binding.imgLogInResult.setImageResource(R.color.color_orange_text_gray)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this


        val dm = DisplayMetrics()

        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager

        wm.defaultDisplay.getMetrics(dm)


        val exSevenCalendar = binding.exSevenCalendar

        exSevenCalendar.dayWidth = dm.widthPixels / 7   //origin is 5

        exSevenCalendar.dayHeight = (exSevenCalendar.dayWidth * 1.6).toInt()  //origin is 1.25


        class DayViewContainer(view: View) : ViewContainer(view) {
            val dayText = view.exSevenDayText
            val dateText = view.exSevenDateText
            val monthText = view.exSevenMonthText
            val selectedView = view.exSevenSelectedView
            lateinit var day: CalendarDay

            init {
                view.setOnClickListener {
                    val firstDay = exSevenCalendar.findFirstVisibleDay()
                    val lastDay = exSevenCalendar.findLastVisibleDay()

                    if (firstDay == day) {
                        /** If the first date on screen was clicked, we scroll to the date to ensure
                         * it is fully visible if it was partially off the screen when clicked.
                         */
                        exSevenCalendar.smoothScrollToDate(day.date)
                    } else if (lastDay == day) {
                        /** If the last date was clicked, we scroll to 4 days ago, this forces the
                         * clicked date to be fully visible if it was partially off the screen.
                         * We scroll to 4 days ago because we show max of five days on the screen
                         * so scrolling to 4 days ago brings the clicked date into full visibility at the end of the calendar view.
                         */
                        exSevenCalendar.smoothScrollToDate(day.date)
                    }


                    /** Example: If you want the clicked date to always be centered on the screen,
                     * you would use: exSevenCalendar.smoothScrollToDate(day.date.minusDays(2))
                     */
                    if (selectedDate != day.date) {
                        val oldDate = selectedDate
                        selectedDate = day.date
                        viewModel.pageStatus.value = 1


                        exSevenCalendar.notifyDateChanged(day.date)
                        oldDate?.let { exSevenCalendar.notifyDateChanged(it) }


                        val serverDataFilter = viewModel.studyRoomdatas.value?.let {
                            it.filter {
                                it.local_date == day.date.toString()
                            }
                        }
                        Log.d(TAG, "serverDataFilter $serverDataFilter")

                        if (serverDataFilter != null) {
                            if (serverDataFilter.isNotEmpty()) {
                                (activity as MainActivity).binding.imgLogInResult.setImageResource(R.color.Color_White_ffffff)
                                viewModel.pageStatus.value = 1
                                binding.seatView.visibility = View.VISIBLE
                                val filtedSeatList = serverDataFilter[0].seatList
                                Log.d(TAG, "test_3 $filtedSeatList")
                                viewModel.studyRoomdataSeats.value = filtedSeatList
                            }else{
                                (activity as MainActivity).binding.imgLogInResult.setImageResource(R.color.color_orange_text_gray)
                                viewModel.pageStatus.value = 0
                                binding.seatView.visibility = View.INVISIBLE
                                (binding.orderedTimeRecycler.adapter as OrderedAdapter).submitList(null)

                            }
                        }

                    }

                }
            }

            fun bind(day: CalendarDay) {
                this.day = day
                dateText.text = dateFormatter.format(day.date)
                dayText.text = dayFormatter.format(day.date)
                monthText.text = monthFormatter.format(day.date)
                dateText.setTextColor(view.context.getColorCompat(if (day.date == selectedDate) R.color.color_orange_reverse_blue else R.color.Color_White_ffffff))
                selectedView.isVisible = day.date == selectedDate
                Log.d(TAG, "selected day is ${day.date}")
            }
        }

        exSevenCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) = container.bind(day)
        }

        val currentMonth = YearMonth.now()
        // Value for firstDayOfWeek does not matter since inDates and outDates are not generated.
        exSevenCalendar.setup(currentMonth, currentMonth, DayOfWeek.values().random())
        exSevenCalendar.scrollToDate(LocalDate.now())


        binding.orderedTimeRecycler.adapter = OrderedAdapter(viewModel)



        viewModel.studyRoomdataSeats.observe(this, Observer { seatListOnline ->
            seatListOnline.let { it ->
                /**
                 * 繪製座位圖  seatTable
                 */
                val seatTable = binding.seatView
                seatTable.setBackgroundResource(R.color.Color_White_ffffff)
                seatTable.isDrawOverviewBitmap = true
                seatTable.autoScale()
                seatTable.setData(4, 4)
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

                        val seatTableFilter = it.filter {
                            it.orderedTimes?.firstTimeSlot != ""
                                    && it.orderedTimes?.secTimeSlot != ""
                                    && it.orderedTimes?.thirdTimeSlot != ""
                        }

                        for (i in seatTableFilter) {
                            if (row == i.row && column == i.column)
                                return true
                        }
                        return false
                    }

                    override fun checked(row: Int, column: Int) {
                        val seatTableFilter = it.filter {
                            it.column == column && it.row == row
                        }
                        val orderedSeatTime = listOf(seatTableFilter[0].orderedTimes)
                        (binding.orderedTimeRecycler.adapter as OrderedAdapter).submitList(
                            orderedSeatTime
                        )
                        (binding.orderedTimeRecycler.adapter as OrderedAdapter).notifyDataSetChanged()
//                        Toast.makeText(
//                            requireContext(),
//                            "$row $column i was checked",
//                            Toast.LENGTH_SHORT
//                        ).show()
                    }

                    override fun unCheck(row: Int, column: Int) {
                        (binding.orderedTimeRecycler.adapter as OrderedAdapter).submitList(null)
                    }

                    override fun checkedSeatTxt(row: Int, column: Int): Array<String>? {
                        return null
                    }

                })
            }
        })
        return binding.root
    }
}
