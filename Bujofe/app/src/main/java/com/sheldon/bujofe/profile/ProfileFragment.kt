package com.sheldon.bujofe.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sheldon.bujofe.MainActivity
import com.sheldon.bujofe.R
import com.sheldon.bujofe.databinding.FragmentProfileBinding
import com.sheldon.bujofe.login.UserManager

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
        viewModel.userId.value = UserManager.userId



        viewModel.userName.value = UserManager.userName

        viewModel.userEmail.value = UserManager.userEmail

        viewModel.userPhotoUrl.value =UserManager.userPhotoUrl

        viewModel.serverUserDataList.observe(this, Observer {
            it.let {
                viewModel.checkerServerUserData(viewModel.userId.value.toString())
            }
        })

        viewModel.userClassList.observe(this, Observer {
            it.let {
                (binding.profileDetailRecycler.adapter as ProfileDetailAdapter).submitList(it)
                (binding.profileDetailRecycler.adapter as ProfileDetailAdapter).notifyDataSetChanged()
            }
        })

        return binding.root
    }
}
