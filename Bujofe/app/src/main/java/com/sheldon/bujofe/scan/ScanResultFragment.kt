package com.sheldon.bujofe.scan

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.sheldon.bujofe.BujofeApplication
import com.sheldon.bujofe.MainActivity
import com.sheldon.bujofe.databinding.FragmentScanResultBinding

class ScanResultFragment : AppCompatDialogFragment() {


    private val viewModel: ScanResultViewModel by lazy {
        ViewModelProviders.of(this).get(ScanResultViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentScanResultBinding.inflate(inflater, container, false)


        val application = requireNotNull(activity).application
        binding.lifecycleOwner = this

        val qrCode = ScanResultFragmentArgs.fromBundle(arguments!!).rollName


        val viewModelFactory = ScanFactory(qrCode, application)

        binding.viewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(ScanResultViewModel::class.java)



        viewModel.rollName.observe(this, Observer {
            it?.let {
                viewModel.TimeToString()
            }
        })


        val timer = object: CountDownTimer(6000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                findNavController().navigate(ScanResultFragmentDirections.actionGlobalHomeFragment())
            }
        }
        timer.start()



        return binding.root
    }



}
