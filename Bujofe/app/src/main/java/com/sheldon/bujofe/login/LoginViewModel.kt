package com.sheldon.bujofe.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.data.Users
import com.sheldon.bujofe.util.Logger

class LoginViewModel : ViewModel() {

    private val TAG: String = "LoginViewModel"

    private val serverUserInformation = MutableLiveData<List<Users>>()

    private val userData = mutableListOf<Users>()

    init {
        getdUserDataFirebase()
    }

    fun serverUserIdChecker(uid: String) {

        val filedUser = serverUserInformation.value?.let {
            it.filter { users ->
                users.uid == uid
            }
        }
        if (filedUser.isNullOrEmpty()) {

            addNewUser()

            Logger.d(TAG + "filedUser = ${filedUser.toString()}")

        } else {

            Logger.d(TAG + "filedUsers = $filedUser")
        }
    }

    /**
     * get all Users Data from Server
     * */
    private fun getdUserDataFirebase() {

        FirebaseFirestore.getInstance().collection("users")
            .get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    for (document in task.result!!) {

                        userData.add(document.toObject(Users::class.java))

                        serverUserInformation.value = userData
                    }
                } else {

                    Logger.w(TAG + "Error getting documents." + task.exception)
                }
            }
    }

    private fun addNewUser() {

        val newUser = Users(
            uid = UserManager.userId.toString(),
            name = UserManager.userName.toString(),
            email = UserManager.userEmail.toString()
        )

        FirebaseFirestore.getInstance().collection("users")
            .add(newUser)
            .addOnSuccessListener { documentReference ->
                Logger.d(TAG + "DocumentSnapshot added with ID: " + documentReference.id)
            }
            .addOnFailureListener { e ->
                Logger.w(TAG + "Error adding document" + e)
            }
    }
}