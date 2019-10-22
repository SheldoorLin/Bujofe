package com.sheldon.bujofe.scan

import android.icu.text.SimpleDateFormat
import android.util.Log
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

    private val tagString: String = "ScanViewModel"

    val scanResultFromQRcode = MutableLiveData<QRcode>()

    val scannedDataToResultPage = MutableLiveData<QRcode>()

    val flag = MutableLiveData<Boolean>()

    private val _teachLists = MutableLiveData<List<TeachList>>()
    private val teachLists: LiveData<List<TeachList>>
        get() = _teachLists


    private val teachListsOnline: ArrayList<TeachList> = ArrayList()
    val userName = MutableLiveData<String>()

    init {
        userName.value = UserManager.userName
        getTeachListFirebase()

    }


    fun displayScanComplete() {
        scannedDataToResultPage.value = null
    }


    private fun getTeachListFirebase() {
        FirebaseFirestore.getInstance().collection("teachList")
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
                Logger.d(tagString + "Error getting documents: " + exception)
            }
    }


    fun getTeacherList() {
        var scanTimestamp = ""
        scanResultFromQRcode.value?.let {
            val date = Date(it.timestamp)
            val format = SimpleDateFormat("yyyy-MM-dd")
            scanTimestamp = format.format(date)
        }
        Logger.d("scanTimestamp = $scanTimestamp")

        Log.d("Sheldon","userName == ${userName.value.toString()}")
        for (item in teachLists.value!!) {
            Logger.i("result of if ${ item.classSize.contains(userName.value.toString())}")
            Logger.i("classSize contain ==== ${item.classSize}")
            if (item.title == scanResultFromQRcode.value?.title && item.classSize.contains(userName.value.toString())) {
                Logger.d("item = $item")
                for (date_item in item.dateList) {
                    val format = SimpleDateFormat("yyyy-MM-dd")
                    val serverListItem = format.format(date_item.date.toDate())
                    if (serverListItem == scanTimestamp) {
                        date_item.rollNameList.add(userName.value.toString())
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
                Logger.d("I'm here == $scanItem")
                if (item.title == scanItem.title) {

                    teachLists.value?.filter { it.title == scanItem.title }
                        ?.get(0)?.let {
                            Log.d("Sheldon","post teachList = $it")
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
