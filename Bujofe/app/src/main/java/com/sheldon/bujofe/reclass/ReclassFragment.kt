package com.sheldon.bujofe.reclass

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sheldon.bujofe.BujofeApplication
import com.sheldon.bujofe.MainActivity
import com.sheldon.bujofe.R
import com.sheldon.bujofe.`object`.ReClass
import com.sheldon.bujofe.databinding.FragmentReclassBinding

class ReclassFragment : Fragment() {

    private val TAG: String = "ReclassFragment"

    private val viewModel: ReclassViewModel by lazy {
        ViewModelProviders.of(this).get(ReclassViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentReclassBinding.inflate(inflater)
        (activity as MainActivity).binding.toolbar.visibility = View.VISIBLE
        (activity as MainActivity).binding.imgLogInResult.setImageResource(R.color.Color_White_ffffff)
        binding.lifecycleOwner = this

        binding.reclassRecorderRecycler.adapter = ReclassAdapter()


        viewModel.userid.value =
            BujofeApplication.instance.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
                .getString("uid", "")


        viewModel.servieceUserinformation.observe(this, Observer {
            it.let {
                viewModel.uidfileChecker(viewModel.userid.value.toString())
            }
        })



        viewModel.userRecordsList.observe(this, Observer {
            it.let {
                Log.d(TAG, "userRecordsList $it")
                (binding.reclassRecorderRecycler.adapter as ReclassAdapter).submitList(it)
                (binding.reclassRecorderRecycler.adapter as ReclassAdapter).notifyDataSetChanged()
            }
        })
//        mock data
//        val test_data_2: ArrayList<ReClass> = ArrayList()
//        test_data_2.add(ReClass("2017/08/09", "芙丸英文", "Approved", "TV補課", "2018/09/08","1-1 關係代名詞"))
//        test_data_2.add(ReClass("2017/08/19", "超凡數學", "Rejected", "超凡老師", "2018/09/17","1-3 微積分先修"))
//        test_data_2.add(ReClass("2017/08/29", "添財歷史", "Approved", "TV補課", "2018/09/18","2-1 歐洲歷史"))
//        test_data_2.add(ReClass("2017/08/39", "飛翔地理", "Waiting", "TV補課", "2018/09/08","3-1 歐亞大陸"))
//        test_data_2.add(ReClass("2018/09/09", "天兵物理", "Approved", "TV補課", "2019/09/11","2-2 衝量"))
//        test_data_2.add(ReClass("2019/09/10", "飛翔地理", "Approved", "TV補課", "2019/09/13","3-3 非洲大陸"))
//        test_data_2.add(ReClass("2019/09/11", "天兵物理", "Approved", "TV補課", "2019/09/16","2-3 動量守恆"))
//        test_data_2.add(ReClass("2019/09/25", "吳用化學", "Rejected", "吳用老師", "2019/09/26","3-4 緩衝溶液"))
//        test_data_2.add(ReClass("2019/09/30", "添財歷史", "Approved", "TV補課", "2019/09/31","1-1 國共內戰"))
//        test_data_2.add(ReClass("2019/10/05", "超凡數學", "Rejected", "TV補課", "2019/10/10","1-2 指數"))


//        (binding.reclassRecorderRecycler.adapter as ReclassAdapter).submitList(test_data_2)

        return binding.root
    }
}
