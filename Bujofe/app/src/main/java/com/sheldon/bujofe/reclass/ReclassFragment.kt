package com.sheldon.bujofe.reclass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sheldon.bujofe.MainActivity
import com.sheldon.bujofe.`object`.ReClass
import com.sheldon.bujofe.databinding.FragmentReclassBinding

class ReclassFragment : Fragment() {

    private val viewModel: ReclassViewModel by lazy {
        ViewModelProviders.of(this).get(ReclassViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentReclassBinding.inflate(inflater)
        (activity as MainActivity).binding.toolbar.visibility = View.VISIBLE
        binding.lifecycleOwner = this

        binding.reclassRecorderRecycler.adapter = ReclassAdapter()


//        mock data
        val test_data_2: ArrayList<ReClass> = ArrayList()
        test_data_2.add(ReClass("2017/08/09", "芙丸英文", "2pm", "4pm", "Approved"))
        test_data_2.add(ReClass("2017/08/19", "超凡數學", "3pm", "5pm", "Rejected"))
        test_data_2.add(ReClass("2017/08/29", "添財歷史", "6pm", "9pm", "Approved"))
        test_data_2.add(ReClass("2017/08/39", "飛翔地理", "12am", "1pm", "Waiting"))
        test_data_2.add(ReClass("2018/09/09", "天兵物理", "4pm", "5pm", "Approved"))
        test_data_2.add(ReClass("2019/09/10", "飛翔地理", "3pm", "6pm", "Approved"))
        test_data_2.add(ReClass("2019/09/11", "天兵物理", "5pm", "7pm", "Approved"))
        test_data_2.add(ReClass("2019/09/25", "吳用化學", "7pm", "8pm", "Rejected"))
        test_data_2.add(ReClass("2019/09/30", "添財歷史", "8pm", "10pm", "Approved"))
        test_data_2.add(ReClass("2019/10/05", "超凡數學", "5pm", "9pm", "Rejected"))


        (binding.reclassRecorderRecycler.adapter as ReclassAdapter).submitList(test_data_2)

        return binding.root
    }
}
