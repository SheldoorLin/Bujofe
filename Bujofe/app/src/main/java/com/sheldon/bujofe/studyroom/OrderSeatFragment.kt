package com.sheldon.bujofe.studyroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
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
        val binding = FragmentOrderSeatBinding.inflate(inflater, container, false)





        return binding.root
    }

}
