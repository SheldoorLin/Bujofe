package com.sheldon.bujofe.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sheldon.bujofe.MainActivity
import com.sheldon.bujofe.R
import com.sheldon.bujofe.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        (activity as MainActivity).binding.toolbar.visibility = View.VISIBLE

        (activity as MainActivity).binding.imgLogInResult.setImageResource(R.color.color_orange_text_gray)

        binding.lifecycleOwner = this

        binding.homeRecyclerViewNotice.adapter = NoticeItemAdapter()

        viewModel.getNoticeFirebase()

        viewModel.notices.observe(this, Observer {
            it.let {
                (binding.homeRecyclerViewNotice.adapter as NoticeItemAdapter).submitList(it)
                (binding.homeRecyclerViewNotice.adapter as NoticeItemAdapter).notifyDataSetChanged()
            }
        })

        return binding.root
    }
}
