package com.sheldon.bujofe.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.BujofeApplication
import com.sheldon.bujofe.`object`.Users

class LoginViewModel : ViewModel() {

    private val TAG: String = "LoginViewModel"


    private val _serverUserInformation = MutableLiveData<List<Users>>()
    val serverUserInformation: LiveData<List<Users>>
        get() = _serverUserInformation


    private val userData = mutableListOf<Users>()

    init {
        getdUserDataFirebase()
    }


    fun uidChecker(uid: String) {

        val filedUser = serverUserInformation.value?.let {
            it.filter { users ->
                users.uid == uid
            }
        }
        if (filedUser.isNullOrEmpty()) {

            addNewUser(uid)
            Log.d(TAG,"filedUser = ${filedUser.toString()}")
        } else {
            Log.d(TAG,"filedUsers = $filedUser")
        }
    }


    /**
     * get all Users Data from Server
     * */
    fun getdUserDataFirebase() {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {

                        Log.d(TAG, "${document.id} => ${document.data}")

                        val data = document.toObject(Users::class.java)

                        userData.add(data)

                        _serverUserInformation.value = userData
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