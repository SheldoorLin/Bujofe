package com.sheldon.bujofe.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.BujofeApplication
import com.sheldon.bujofe.`object`.Users

class LoginViewModel : ViewModel() {

    private val TAG: String = "LoginViewModel"


    private val _serviece_userInformation = MutableLiveData<List<Users>>()
    val serviece_userInformation: LiveData<List<Users>>
        get() = _serviece_userInformation


    private val _userInformation = MutableLiveData<String>()
    val userInformation: LiveData<String>
        get() = _userInformation

    private val userData = mutableListOf<Users>()

    init {
        sendLoginInformation()
    }


    fun uidChecker(uid: String) {

        val filedUser = serviece_userInformation.value?.let {
            it.filter { users ->
                users.uid == uid
            }
        }

        if (filedUser.isNullOrEmpty()) {
            addNewUser(uid)
        }
    }


    /**
     * get all Users Data to Service
     * */
    fun sendLoginInformation() {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.d(TAG, document.id + " => " + document.data)
                        val data = document.toObject(Users::class.java)
                        userData.add(data)
                        _serviece_userInformation.value = userData
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
    }


    fun addNewUser(uid: String) {
        val user = Users(uid)

        /**
         * SharedPreferences
         * */
        BujofeApplication.instance.getSharedPreferences("userProfile", Context.MODE_PRIVATE)?.edit()
            ?.putString("uid", uid)?.apply()

        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .add(user)
            .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.id)
            })
            .addOnFailureListener(OnFailureListener { e -> Log.w(TAG, "Error adding document", e) })
    }


}