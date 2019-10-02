package com.sheldon.bujofe.scan

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.sheldon.bujofe.BujofeApplication
import com.sheldon.bujofe.`object`.QRcode
import com.sheldon.bujofe.`object`.TeachList
import java.util.*
import kotlin.collections.ArrayList

class ScanViewModel : ViewModel() {
    private val TAG: String = "ScanViewModel"
    val preScanResult = MutableLiveData<QRcode>()
    val scanResults = MutableLiveData<QRcode>()
    val flag = MutableLiveData<Boolean>()

    private val _teachLists = MutableLiveData<List<TeachList>>()
    val teachLists: LiveData<List<TeachList>>
        get() = _teachLists


    private val teachListsOnline: ArrayList<TeachList> = ArrayList()
    val userName =
        BujofeApplication.instance.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
            .getString("displayName", "")

    init {
        getTeachListFirebase()

    }


    fun displayScanComplete() {
        scanResults.value = null
    }


    fun getTeachListFirebase() {
        val db = FirebaseFirestore.getInstance()
        db.collection("teachList")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.toObject(TeachList::class.java)
                    teachListsOnline.add(data)
                    val index = teachListsOnline.size - 1
                    teachListsOnline[index].id = document.id
                    _teachLists.value = teachListsOnline
//                    flag.value = true
                }
            }.addOnFailureListener { exception ->
                Log.d(
                    TAG,
                    "Error getting documents: ",
                    exception
                )
            }

    }


    fun getTeacherList() {
        var scanTimestamp: String = ""
        preScanResult.value?.let {
            val date = Date(it.timestep)
            val format = SimpleDateFormat("yyyy-MM-dd")
            scanTimestamp = format.format(date)
        }
        Log.d("scanTimestamp", "scanTimestamp = $scanTimestamp")


        for (item in teachLists.value!!) {
            if (item.title == preScanResult.value?.title) {
                Log.d("item", "item = $item")
                if (item.class_size.contains(userName)) {
                    for (date_item in item.dateList) {
                        if (date_item.date == scanTimestamp) {
                            date_item.rollNameList.add(userName!!)
                            Log.d("date_item", "date_item = ${date_item.rollNameList}")
                        }
                    }
                }
            }
        }
        Log.d("teachLists","teachLists.value = ${teachLists.value}")
    }


    fun setNewData() {
        for (item in teachLists.value!!) {

            if (item.title == "飛帆英文") {

                val teachListFilter = teachLists.value!!.filter {
                    it.title == "飛帆英文"
                }


                val db = FirebaseFirestore.getInstance()

                val washingtonRef = db.collection("teachList").document(item.id)


                washingtonRef.set(teachListFilter[0], SetOptions.merge())
            }
        }
    }

}
