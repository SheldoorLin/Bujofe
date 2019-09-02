package com.sheldon.bujofe.reclass

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sheldon.bujofe.R
import com.sheldon.bujofe.databinding.FragmentReclassBinding

class ReclassFragment : Fragment() {

    private val viewModel: ReclassViewModel by lazy {
        ViewModelProviders.of(this).get(ReclassViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentReclassBinding.inflate(inflater, container, false)



        return binding.root
    }
}
