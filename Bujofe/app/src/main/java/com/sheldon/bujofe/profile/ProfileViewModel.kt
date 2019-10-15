package com.sheldon.bujofe.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.data.ClassList
import com.sheldon.bujofe.data.Users
import com.sheldon.bujofe.util.Logger

class ProfileViewModel : ViewModel() {

    private val TAG: String = "ProfileViewModel"

    val userid = MutableLiveData<String>()
    val userName = MutableLiveData<String>()
    val userPhotoUrl = MutableLiveData<String>()
    val userEmail = MutableLiveData<String>()

    private val _serverUserDataList = MutableLiveData<List<Users>>()
    val serverUserDataList: LiveData<List<Users>>
        get() = _serverUserDataList


    private val _userClassList = MutableLiveData<List<ClassList>>()
    val userClassList: LiveData<List<ClassList>>
        get() = _userClassList

    init {
        getUserListDataFromFirebase()
    }

    private fun getUserListDataFromFirebase() {
        FirebaseFirestore.getInstance().collection("users")
            .get()
            .addOnCompleteListener { task ->
                val userDataList = mutableListOf<Users>()
                if (task.isSuccessful) {
                    for (document in task.result!!) {

                        userDataList.add(document.toObject(Users::class.java))

                        _serverUserDataList.value = userDataList
                    }
                } else {
                    Logger.w(TAG + "Error getting documents." + task.exception)
                }
            }
    }

    fun checkerServerUserData(uid: String) {
        val filedUser = serverUserDataList.value?.let {
            it.filter { users ->
                users.uid == uid
            }
        }
        if (filedUser.isNullOrEmpty()) {
            Logger.d("filedUser.toString() = ${filedUser.toString()}")
        } else {
            _userClassList.value = filedUser[0].classList
        }
    }
}
