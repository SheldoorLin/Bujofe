package com.sheldon.bujofe.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sheldon.bujofe.R
import com.sheldon.bujofe.`object`.ClassInformation
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

        binding.lifecycleOwner = this

        binding.profileDetailRecycler.adapter = ProfileDetailAdapter()


//        mock data
        val testList: ArrayList<ClassInformation> = ArrayList()
        testList.add(ClassInformation("吳用化學", 30, 2))
        testList.add(ClassInformation("芙丸英文", 30, 2))
        testList.add(ClassInformation("超凡數學", 30, 2))
        testList.add(ClassInformation("添財歷史", 30, 2))
        testList.add(ClassInformation("飛翔地理", 30, 2))
        testList.add(ClassInformation("天兵物理", 30, 2))
        testList.add(ClassInformation("笨拙家政", 30, 2))


        binding.btnReplacementApply.setOnClickListener {
            this.viewModel.firebase()
        }



        (binding.profileDetailRecycler.adapter as ProfileDetailAdapter).submitList(testList)



        return binding.root
    }
}
