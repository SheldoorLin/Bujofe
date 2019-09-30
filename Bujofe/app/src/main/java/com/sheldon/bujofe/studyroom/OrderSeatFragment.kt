package com.sheldon.bujofe.studyroom

import android.app.Activity
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.sheldon.bujofe.databinding.FragmentOrderSeatBinding
import com.sheldon.bujofe.scan.ScanFactory
import com.sheldon.bujofe.scan.ScanResultFragmentArgs
import com.sheldon.bujofe.scan.ScanResultViewModel


class OrderSeatFragment : AppCompatDialogFragment() {


    private val viewModel: OrderSeatViewModel by lazy {
        ViewModelProviders.of(this).get(OrderSeatViewModel::class.java)
    }

    private val TAG = "OrderSeatFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOrderSeatBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        val application = requireNotNull(activity).application
        binding.lifecycleOwner = this

        val seatOrder = OrderSeatFragmentArgs.fromBundle(arguments!!).seatOrder

        val viewModelFactory = OrderSeatFactory(seatOrder, application)

        binding.viewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(OrderSeatViewModel::class.java)


//        val checkedId = viewModel.seatOrders.value?.id
//        for(item in viewModel.seatOrders.value?.originSeatList!!.seatList){
//            if (item.id == checkedId){
//
//            }
//        }


        viewModel.seatOrders.observe(this, Observer {
            it.let {
                binding.txSeatRderId.text = it.id
            }
        })

        binding.btnYes.setOnClickListener {
            viewModel.setNewData()
            this.findNavController()
                .navigate(OrderSeatFragmentDirections.actionOrderSeatFragmentToOrderResultFragment())
            onStop()
        }
        binding.btnNo.setOnClickListener {
            onStop()
        }

        return binding.root
    }


}

