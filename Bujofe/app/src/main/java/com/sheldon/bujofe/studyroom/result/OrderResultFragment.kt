package com.sheldon.bujofe.studyroom.result

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.navigation.fragment.findNavController
import com.sheldon.bujofe.databinding.FragmentOrderResultBinding



class OrderResultFragment : AppCompatDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentOrderResultBinding.inflate(inflater, container, false)

        val timer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                findNavController().navigate(OrderResultFragmentDirections.actionGlobalStudyRoomFragment())
            }
        }
        timer.start()

        return binding.root
    }

}
