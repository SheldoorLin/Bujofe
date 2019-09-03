package com.sheldon.bujofe.scan

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sheldon.bujofe.R
import com.sheldon.bujofe.databinding.FragmentScanBinding

class ScanFragment : Fragment() {


    private lateinit var viewModel: ScanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentScanBinding.inflate(inflater, container, false)

        return binding.root
    }

}
