package com.sheldon.bujofe.scan

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sheldon.bujofe.`object`.QRcode

class ScanFactory(
    private val qrCode: QRcode,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("uncheck_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScanResultViewModel::class.java)){
            return ScanResultViewModel(qrCode,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}