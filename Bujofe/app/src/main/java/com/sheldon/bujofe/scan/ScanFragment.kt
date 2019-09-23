package com.sheldon.bujofe.scan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.sheldon.bujofe.MainActivity
import com.sheldon.bujofe.`object`.QRcode
import com.sheldon.bujofe.databinding.FragmentScanBinding


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

        val scannerView = binding.scannerView
        val activity = requireActivity()




        Dexter.withActivity(activity)
            .withPermission(android.Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {

                    codeScanner = CodeScanner(activity, scannerView)
                    codeScanner?.decodeCallback = DecodeCallback {
                        activity.runOnUiThread {
                            Toast.makeText(activity, it.text, Toast.LENGTH_LONG).show()
                            viewModel.scanResults.value = QRcode(it.text, it.timestamp)
                        }
                    }
                    scannerView.setOnClickListener {
                        codeScanner?.startPreview()
                    }

                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {

                }
            })
            .check()
        viewModel.scanResults.observe(this, Observer {
            it?.let {
                this.findNavController()
                    .navigate(ScanFragmentDirections.actionScanFragmentToScanResultFragment(it))
                viewModel.displayScanComplete()
            }
        })

        return binding.root
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
