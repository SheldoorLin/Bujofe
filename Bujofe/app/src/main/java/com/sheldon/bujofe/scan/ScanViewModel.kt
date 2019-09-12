package com.sheldon.bujofe.scan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sheldon.bujofe.`object`.QRcode

class ScanViewModel : ViewModel() {


    val scanResults = MutableLiveData<QRcode>()


    fun displayScanComplete() {
        scanResults.value = null
    }


}
