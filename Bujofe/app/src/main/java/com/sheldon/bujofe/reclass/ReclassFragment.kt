package com.sheldon.bujofe.reclass

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sheldon.bujofe.MainActivity
import com.sheldon.bujofe.R
import com.sheldon.bujofe.databinding.FragmentReclassBinding
import com.sheldon.bujofe.login.UserManager

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
        
        viewModel.userid.value = UserManager.userId

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

        return binding.root
    }
}
