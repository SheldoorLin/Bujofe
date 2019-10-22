package com.sheldon.bujofe.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.data.Users
import com.sheldon.bujofe.util.Logger

class LoginViewModel : ViewModel() {

    private val tagString: String = "LoginViewModel"

    val serverUserInformation = MutableLiveData<List<Users>>()

    private val userData = mutableListOf<Users>()

    init {
        getdUserDataFirebase()
    }

    fun serverUserIdChecker(uid: String) {

        Logger.d(tagString + "uid = $uid")

        Logger.d(tagString+"    serverUserInformation.value == ${serverUserInformation.value}")
        val filedUser = serverUserInformation.value?.let {
            it.filter { users ->
                users.uid == uid
            }
        }
        if (filedUser.isNullOrEmpty()) {

            addNewUser(uid)

            Logger.d(tagString + "filedUser = ${filedUser.toString()}")

        } else {

            Logger.d(tagString + "filedUsers = $filedUser")
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

                    Logger.w(tagString + "Error getting documents." + task.exception)
                }
            }
    }

    private fun addNewUser(uid: String) {

        val newUser = Users(
            uid = uid,
            name = UserManager.userName.toString(),
            email = UserManager.userEmail.toString()
        )

        FirebaseFirestore.getInstance().collection("users")
            .add(newUser)
            .addOnSuccessListener { documentReference ->
                Logger.d(tagString + "DocumentSnapshot added with ID: " + documentReference.id)
            }
            .addOnFailureListener { e ->
                Logger.w(tagString + "Error adding document" + e)
            }
    }
}