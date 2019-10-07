package com.sheldon.bujofe.scan

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.sheldon.bujofe.login.UserManager
import com.sheldon.bujofe.data.QRcode
import com.sheldon.bujofe.data.TeachList
import com.sheldon.bujofe.util.Logger
import java.util.*
import kotlin.collections.ArrayList

class ScanViewModel : ViewModel() {

    private val TAG: String = "ScanViewModel"

    val scanResultFromQRcode = MutableLiveData<QRcode>()

    val transmitScanedDatasToResultPage = MutableLiveData<QRcode>()

    val flag = MutableLiveData<Boolean>()

    private val _teachLists = MutableLiveData<List<TeachList>>()
    val teachLists: LiveData<List<TeachList>>
        get() = _teachLists


    private val teachListsOnline: ArrayList<TeachList> = ArrayList()
    val userName = UserManager.userName

    init {
        getTeachListFirebase()
    }


    fun displayScanComplete() {
        transmitScanedDatasToResultPage.value = null
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
                }
            }.addOnFailureListener { exception ->
                Logger.d(TAG + "Error getting documents: " + exception)
            }
    }


    fun getTeacherList() {
        var scanTimestamp: String = ""
        scanResultFromQRcode.value?.let {
            val date = Date(it.timestamp)
            val format = SimpleDateFormat("yyyy-MM-dd")
            scanTimestamp = format.format(date)
        }
        Logger.d("scanTimestamp = $scanTimestamp")


        for (item in teachLists.value!!) {
            if (item.title == scanResultFromQRcode.value?.title && item.classSize.contains(userName)) {
                Logger.d("item = $item")
                for (date_item in item.dateList) {
                    if (date_item.date.seconds * 1000 == scanTimestamp.toLong()) {
                        date_item.rollNameList.add(userName!!)
                        Logger.d("date_item = ${date_item.rollNameList}")
                    }
                }
            }
        }
        Logger.d("teachLists.value = ${teachLists.value}")
    }


    fun setNewData() {
        for (item in teachLists.value!!) {
            scanResultFromQRcode.value?.let { scanItem ->

                if (item.title == scanItem.title) {

                    teachLists.value?.filter { it.title == scanItem.title }
                        ?.get(0)?.let {
                            FirebaseFirestore.getInstance()
                                .collection("teachList")
                                .document(item.id)
                                .set(it, SetOptions.merge())
                        }
                }
            }
        }
    }
}
