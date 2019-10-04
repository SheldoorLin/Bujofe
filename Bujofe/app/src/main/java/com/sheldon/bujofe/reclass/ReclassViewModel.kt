package com.sheldon.bujofe.reclass

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.data.Records
import com.sheldon.bujofe.data.TeachList
import com.sheldon.bujofe.data.Users
import com.sheldon.bujofe.util.Logger

class ReclassViewModel : ViewModel() {

    private val TAG: String = "ReclassViewModel"

    val userid = MutableLiveData<String>()

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
        FirebaseFirestore.getInstance().collection("users")
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
                        Logger.d(TAG + "teachListData $teachListData")
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
            Logger.d("filedUser ${filedUser.toString()}")
        } else {
            Logger.d("filedUser123 $filedUser")
            _userRecordsList.value = filedUser[0].records
            Logger.d("userClassList value = ${userRecordsList.value}")
        }
    }
}
