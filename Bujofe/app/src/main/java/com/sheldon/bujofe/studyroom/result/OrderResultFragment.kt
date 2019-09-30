package com.sheldon.bujofe.studyroom.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.sheldon.bujofe.databinding.FragmentOrderResultBinding


class OrderResultFragment : AppCompatDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentOrderResultBinding.inflate(inflater, container, false)


        return binding.root
    }

}
