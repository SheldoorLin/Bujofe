package com.sheldon.bujofe.studyroom

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.sheldon.bujofe.BujofeApplication

import com.sheldon.bujofe.R
import com.sheldon.bujofe.`object`.Seat
import com.sheldon.bujofe.databinding.FragmentStudyRoomBinding

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






        /**
         *mock data
         *  */
//        val test_data: ArrayList<Weekday> = ArrayList()
//        test_data.add(Weekday("Sunday", Seat("A01", "王小翔")))
//        test_data.add(Weekday("Sunday", Seat("A02", "王大翔")))
//        test_data.add(Weekday("Sunday", Seat("A03", "王中翔")))
//        test_data.add(Weekday("Sunday", Seat("A04", "Eric")))
//        test_data.add(Weekday("Sunday", Seat("A05", "Sandra")))
//        test_data.add(Weekday("Sunday", Seat("A06", "Sophie")))
//        test_data.add(Weekday("Sunday", Seat("A07", "Sheldon")))
//        test_data.add(Weekday("Sunday", Seat("A08", "Terry")))
//        test_data.add(Weekday("Sunday", Seat("A09", "Eltin")))
//        test_data.add(Weekday("Sunday", Seat("A10", "Wayne")))
        val test_data_2: ArrayList<Seat> = ArrayList()
        test_data_2.add(Seat("A01", "王小翔","ef5350"))
        test_data_2.add(Seat("A02", "王大翔","ef5350"))
        test_data_2.add(Seat("A03", "王中翔","ef5350"))
        test_data_2.add(Seat("A04", "Eric","ef5350"))
        test_data_2.add(Seat("A05", "Sandra","ef5350"))
        test_data_2.add(Seat("A06", "Sophie","ef5350"))
        test_data_2.add(Seat("A07", "Sheldon","ef5350"))
        test_data_2.add(Seat("A08", "Terry","ef5350"))
        test_data_2.add(Seat("A09", "Eltin","ef5350"))
        test_data_2.add(Seat("A10", "Wayne","ef5350"))
        val test_data_3: ArrayList<Seat> = ArrayList()
        test_data_3.add(Seat("A01", "王小翔","ef5350"))
        test_data_3.add(Seat("A02", "王大翔","ef5350"))
        test_data_3.add(Seat("A03", "待預約","00c853"))
        test_data_3.add(Seat("A04", "Eric","ef5350"))
        test_data_3.add(Seat("A05", "Sandra","ef5350"))
        test_data_3.add(Seat("A06", "Sophie","ef5350"))
        test_data_3.add(Seat("A07", "待預約","00c853"))
        test_data_3.add(Seat("A08", "Terry","ef5350"))
        test_data_3.add(Seat("A09", "Eltin","ef5350"))
        test_data_3.add(Seat("A10", "Wayne","ef5350"))







        binding.spinnerWeekofday.adapter = WeekSpinnerAdapter(
            BujofeApplication.instance.resources.getStringArray(R.array.week)
        )

        binding.seatRecycler.adapter = SeatAdapter()

        binding.spinnerWeekofday.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //To change body of created functions use File | Settings | File Templates.
                }

                override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                    when (position) {
                        0 -> (binding.seatRecycler.adapter as SeatAdapter).submitList(test_data_3)
                        1 -> (binding.seatRecycler.adapter as SeatAdapter).submitList(test_data_2)
                        2 -> (binding.seatRecycler.adapter as SeatAdapter).submitList(test_data_2)
                        3 -> (binding.seatRecycler.adapter as SeatAdapter).submitList(test_data_2)
                        4 -> (binding.seatRecycler.adapter as SeatAdapter).submitList(test_data_2)
                        5 -> (binding.seatRecycler.adapter as SeatAdapter).submitList(test_data_2)
                        6 -> (binding.seatRecycler.adapter as SeatAdapter).submitList(test_data_2)
                    }
                }
            }


        return binding.root
    }
}
