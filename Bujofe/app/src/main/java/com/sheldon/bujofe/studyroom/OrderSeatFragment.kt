package com.sheldon.bujofe.studyroom

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.sheldon.bujofe.R
import com.sheldon.bujofe.databinding.FragmentOrderSeatBinding

class OrderSeatFragment : DialogFragment() {



    private val viewModel: OrderSeatViewModel by lazy {
        ViewModelProviders.of(this).get(OrderSeatViewModel::class.java)
    }

    private val TAG = "OrderSeatFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOrderSeatBinding.inflate(inflater,container,false)





        return binding.root
    }
}
