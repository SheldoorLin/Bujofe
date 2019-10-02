package com.sheldon.bujofe.studyroom

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sheldon.bujofe.`object`.QRcode
import com.sheldon.bujofe.`object`.SeatOrder
import com.sheldon.bujofe.scan.ScanResultViewModel

class OrderSeatFactory (
    private val seatOrder: SeatOrder,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("uncheck_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderSeatViewModel::class.java)){
            return OrderSeatViewModel(seatOrder,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}