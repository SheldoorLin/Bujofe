package com.sheldon.bujofe.scan.result

import android.app.Application
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sheldon.bujofe.data.QRcode
import java.util.*

class ScanResultViewModel(qrCode: QRcode, app: Application) : AndroidViewModel(app) {

    private val _rollName = MutableLiveData<QRcode>()
    val rollName: LiveData<QRcode>
        get() = _rollName

    val scanTimestamp = MutableLiveData<String>()

    init {
        _rollName.value = qrCode
    }

    fun timestampToString() {
        rollName.value?.let {
            val date = Date(it.timestamp)
            val format = SimpleDateFormat("yyyy-MM-dd")
            scanTimestamp.value = format.format(date)
            Log.d("scanTimestamp", scanTimestamp.value.toString())
        }
    }
}
