package com.sheldon.bujofe.studyroom

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.sheldon.bujofe.MainActivity
import com.sheldon.bujofe.R
import com.sheldon.bujofe.class_schedule.getColorCompat
import com.sheldon.bujofe.databinding.FragmentStudyRoomBinding
import com.sheldon.bujofe.util.Logger
import kotlinx.android.synthetic.main.item_studyroom_calendar_day.view.*
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter


class StudyRoomFragment : Fragment() {

    private val viewModel: StudyRoomViewModel by lazy {
        ViewModelProviders.of(this).get(StudyRoomViewModel::class.java)
    }

    val tagString = "StudyRoomFragment"

    private var selectedDate: LocalDate? = null

    private val monthFormatter = DateTimeFormatter.ofPattern("MMM")

    private val dateFormatter = DateTimeFormatter.ofPattern("dd")

    private val dayFormatter = DateTimeFormatter.ofPattern("EEE")

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

        val studyRoomOneLineCalendar = binding.studyRoomOneLineCalendar

        studyRoomOneLineCalendar.dayWidth = dm.widthPixels / 7   //origin is 5

        studyRoomOneLineCalendar.dayHeight =
            (studyRoomOneLineCalendar.dayWidth * 1.6).toInt()  //origin is 1.25

        class DayViewContainer(view: View) : ViewContainer(view) {

            val dayText = view.study_room_day

            val dateText = view.study_room_date

            val monthText = view.study_room_month

            val selectedView = view.study_room_day_selected

            lateinit var day: CalendarDay

            init {
                view.setOnClickListener {
                    val firstDay = studyRoomOneLineCalendar.findFirstVisibleDay()
                    val lastDay = studyRoomOneLineCalendar.findLastVisibleDay()

                    if (firstDay == day) {
                        /** If the first date on screen was clicked, we scroll to the date to ensure
                         * it is fully visible if it was partially off the screen when clicked.
                         */
                        studyRoomOneLineCalendar.smoothScrollToDate(day.date)
                    } else if (lastDay == day) {
                        /** If the last date was clicked, we scroll to 4 days ago, this forces the
                         * clicked date to be fully visible if it was partially off the screen.
                         * We scroll to 4 days ago because we show max of five days on the screen
                         * so scrolling to 4 days ago brings the clicked date into full visibility at the end of the calendar view.
                         */
                        studyRoomOneLineCalendar.smoothScrollToDate(day.date)
                    }

                    /** Example: If you want the clicked date to always be centered on the screen,
                     * you would use: exSevenCalendar.smoothScrollToDate(day.date.minusDays(2))
                     */
                    if (selectedDate != day.date) {
                        val oldDate = selectedDate
                        selectedDate = day.date
                        viewModel.pageStatus.value = 1
                        viewModel.clickedDateOnTopCalendar.value = day.date
                        studyRoomOneLineCalendar.notifyDateChanged(day.date)
                        oldDate?.let { studyRoomOneLineCalendar.notifyDateChanged(it) }
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
                Logger.d(tagString + "selected day is ${day.date}")
            }
        }

        studyRoomOneLineCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) = container.bind(day)
        }

        val currentMonth = YearMonth.now()

        // Value for firstDayOfWeek does not matter since inDates and outDates are not generated.
        studyRoomOneLineCalendar.setup(currentMonth, currentMonth, DayOfWeek.values().random())

        studyRoomOneLineCalendar.scrollToDate(LocalDate.now())

        binding.orderedTimeRecycler.adapter = OrderedAdapter(viewModel)

        viewModel.clickedDateOnTopCalendar.observe(this, Observer {
            it.let { localDate ->

                val serverDataFilter =

                    viewModel.serverStudyRoomListData.value?.let { listItem ->

                        listItem.filter { studyRoomSeat ->

                            DateTimeUtils.toLocalDate(java.sql.Date(studyRoomSeat.localDate.time)) == localDate

                        }

                    }

                if (serverDataFilter != null && serverDataFilter.isNotEmpty()) {

                    viewModel.pageStatus.value = 1
                    binding.seatView.visibility = View.VISIBLE
                    viewModel.chosenDate.value = serverDataFilter[0].localDate.toString()
                    viewModel.chosenSeatOnServerDocumentId.value = serverDataFilter[0].documentId
                    viewModel.serverStudyRoomSeatsLists.value = serverDataFilter[0]
                    val filteredSeatList = serverDataFilter[0].seatList
                    viewModel.localStudyRoomSeatsList.value = filteredSeatList
                    (activity as MainActivity).binding.imgLogInResult.setImageResource(R.color.Color_White_ffffff)
                    (binding.orderedTimeRecycler.adapter as OrderedAdapter).notifyDataSetChanged()

                } else {

                    viewModel.pageStatus.value = 0
                    binding.seatView.visibility = View.INVISIBLE
                    (binding.orderedTimeRecycler.adapter as OrderedAdapter).submitList(null)
                    (activity as MainActivity).binding.imgLogInResult.setImageResource(R.color.color_orange_text_gray)

                }
            }
        })

        viewModel.localStudyRoomSeatsList.observe(this, Observer { seatListOnline ->
            seatListOnline.let { it ->

                /**
                 * 繪製座位圖  seatTable
                 */
                val seatTable = binding.seatView
                seatTable.setBackgroundResource(R.color.Color_White_ffffff)
                //seatTable.isDrawOverviewBitmap = true
                seatTable.autoScale()
                seatTable.setData(4, 4)
                seatTable.setScreenName("黑板")
                seatTable.setMaxSelected(1)
                seatTable.setSeatChecker(object : SeatTable.SeatChecker {

                    override fun isValidSeat(row: Int, column: Int): Boolean {
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
                        viewModel.chosenSeatId.value = seatTableFilter[0].id

                        val orderedSeatTime = listOf(seatTableFilter[0].orderedTimes)

                        (binding.orderedTimeRecycler.adapter as OrderedAdapter).submitList(
                            orderedSeatTime
                        )

                        (binding.orderedTimeRecycler.adapter as OrderedAdapter).notifyDataSetChanged()
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

        viewModel.chosenSeat.observe(this, Observer {
            it.let {
                this.findNavController().navigate(
                    StudyRoomFragmentDirections.actionStudyRoomFragmentToOrderSeatFragment(it)
                )
            }
        })

        return binding.root
    }
}
