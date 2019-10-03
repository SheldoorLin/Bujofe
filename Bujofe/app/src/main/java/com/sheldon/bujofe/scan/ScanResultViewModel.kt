package com.sheldon.bujofe.scan

import android.app.Application
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sheldon.bujofe.`object`.QRcode
import java.util.*

class ScanResultViewModel(qrCode: QRcode, app: Application) : AndroidViewModel(app) {


    private val _rollName = MutableLiveData<QRcode>()
    val rollName: LiveData<QRcode>
        get() = _rollName

    val scanTimestamp = MutableLiveData<String>()


    init {
        _rollName.value = qrCode
    }

    fun TimeToString() {
        rollName.value?.let {
            val date = Date(it.timestep)
            val format = SimpleDateFormat("yyyy-MM-dd")
            scanTimestamp.value = format.format(date)
            Log.d("scanTimestamp", scanTimestamp.value.toString())
        }
    }
}
