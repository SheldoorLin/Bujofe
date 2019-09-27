package com.sheldon.bujofe.reclass

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.BujofeApplication
import com.sheldon.bujofe.`object`.ClassList
import com.sheldon.bujofe.`object`.Records
import com.sheldon.bujofe.`object`.TeachList
import com.sheldon.bujofe.`object`.Users

class ReclassViewModel : ViewModel() {

    private val TAG: String = "ReclassViewModel"


    val userid =MutableLiveData<String>()


    private val _servieceUserinformation = MutableLiveData<List<Users>>()
    val servieceUserinformation: LiveData<List<Users>>
        get() = _servieceUserinformation

    private val userData = mutableListOf<Users>()

    private val _userRecordsList = MutableLiveData<List<Records>>()
    val userRecordsList: LiveData<List<Records>>
        get() = _userRecordsList




    private val _serviceTeachListInformation = MutableLiveData<List<TeachList>>()
    val serviece_teachListInformation: LiveData<List<TeachList>>
        get() = _serviceTeachListInformation
    private val teachListData = mutableListOf<TeachList>()





    init {
        getUserDataFirebase()
    }



    fun getUserDataFirebase() {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.d(TAG, document.id + " => " + document.data)
                        val data = document.toObject(Users::class.java)
                        userData.add(data)
                        _servieceUserinformation.value = userData
                        Log.d(TAG, "userData $userData")
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
    }






    fun getClassInformation() {
        val db = FirebaseFirestore.getInstance()
        db.collection("teachList")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.d(TAG, document.id + " => " + document.data)
                        val data = document.toObject(TeachList::class.java)
                        teachListData.add(data)
                        _serviceTeachListInformation.value = teachListData
                        Log.d(TAG, "teachListData $teachListData")
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
    }
    fun uidfileChecker(uid: String) {
        val filedUser = servieceUserinformation.value?.let {
            it.filter { users ->
                users.uid == uid
            }
        }
        if (filedUser.isNullOrEmpty()) {
            Log.d(TAG, "filedUser ${filedUser.toString()}")
        } else {
            Log.d(TAG, "filedUser123 $filedUser")
            _userRecordsList.value = filedUser[0].records
            Log.d(TAG, "userClassList value = ${userRecordsList.value}")

        }
    }

}
