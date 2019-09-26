package com.sheldon.bujofe.profile

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
import com.sheldon.bujofe.`object`.ClassList
import com.sheldon.bujofe.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        (activity as MainActivity).binding.toolbar.visibility = View.VISIBLE
        (activity as MainActivity).binding.imgLogInResult.setImageResource(R.color.Color_White_ffffff)
        binding.lifecycleOwner = this

        binding.profileDetailRecycler.adapter = ProfileDetailAdapter()
        binding.viewModel = viewModel

        /**
         * SharedPreferences
         * */
        viewModel.userid.value =
            BujofeApplication.instance.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
                .getString("uid", "")
        viewModel.userPhotoUrl.value =
            BujofeApplication.instance.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
                .getString("photoUrl", "")
        viewModel.userName.value =
            BujofeApplication.instance.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
                .getString("displayName", "")



        viewModel.serviece_userInformation.observe(this, Observer {
            it.let {
                viewModel.uidfileChecker(viewModel.userid.value.toString())
            }
        })

//
        //mock data
//        val testList: ArrayList<ClassList> = ArrayList()
//        testList.add(ClassList("吳用化學", 30, 2))
//        testList.add(ClassList("芙丸英文", 30, 2))
//        testList.add(ClassList("超凡數學", 30, 2))
//        testList.add(ClassList("添財歷史", 30, 2))
//        testList.add(ClassList("飛翔地理", 30, 2))
//        testList.add(ClassList("天兵物理", 30, 2))
//        testList.add(ClassList("笨拙家政", 30, 2))
//        testList.add(ClassList("天兵物理", 30, 2))
//        testList.add(ClassList("笨拙家政", 30, 2))
//        testList.add(ClassList("笨拙家政", 30, 2))
//        testList.add(ClassList("笨拙家政", 30, 2))
//        testList.add(ClassList("超凡數學", 30, 2))
//        testList.add(ClassList("超凡數學", 30, 2))
//        testList.add(ClassList("超凡數學", 30, 2))
//        binding.btnReplacementApply.setOnClickListener {
//            this.viewModel.firebase()
//        }

        viewModel.userClassList.observe(this, Observer {
            it.let {
                (binding.profileDetailRecycler.adapter as ProfileDetailAdapter).submitList(it)
                (binding.profileDetailRecycler.adapter as ProfileDetailAdapter).notifyDataSetChanged()
            }
        })



        return binding.root
    }
}
