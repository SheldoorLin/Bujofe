package com.sheldon.bujofe.scan

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.sheldon.bujofe.MainActivity
import com.sheldon.bujofe.data.QRcode
import com.sheldon.bujofe.databinding.FragmentScanBinding
import com.sheldon.bujofe.util.Logger


class ScanFragment : Fragment() {

    private val viewModel: ScanViewModel by lazy {
        ViewModelProviders.of(this).get(ScanViewModel::class.java)
    }

    private var codeScanner: CodeScanner? = null

    private lateinit var binding: FragmentScanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as MainActivity).binding.toolbar.visibility = View.VISIBLE

        binding = FragmentScanBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        methodWithPermissions()

        viewModel.scannedDataToResultPage.observe(this, Observer {
            it?.let {

                this.findNavController()
                    .navigate(ScanFragmentDirections.actionScanFragmentToScanResultFragment(it))

                viewModel.displayScanComplete()
            }
        })
        return binding.root
    }

    private fun methodWithPermissions() = runWithPermissions(Manifest.permission.CAMERA) {
        // Do the stuff with permissions safely
        val activity = requireActivity()
        codeScanner = CodeScanner(requireContext(), binding.scannerView)
        codeScanner?.decodeCallback = DecodeCallback {
            activity.runOnUiThread {

                viewModel.scanResultFromQRcode.value = QRcode("飛帆英文", it.timestamp)

                Logger.d("ScanFragment" + "scan result = ${it.text}+${it.timestamp}")

                viewModel.userName.observe(this, Observer {

                    viewModel.getTeacherList()

                    viewModel.setNewData()

                })

                viewModel.scannedDataToResultPage.value = viewModel.scanResultFromQRcode.value
            }
        }
        binding.scannerView.setOnClickListener {
            codeScanner?.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner?.startPreview()
    }

    override fun onPause() {
        codeScanner?.releaseResources()
        super.onPause()
    }
}
