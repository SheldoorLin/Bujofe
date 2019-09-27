package com.sheldon.bujofe.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.BujofeApplication
import com.sheldon.bujofe.`object`.ClassList
import com.sheldon.bujofe.`object`.TeachList
import com.sheldon.bujofe.`object`.Users

class ProfileViewModel : ViewModel() {
    private val TAG: String = "ProfileViewModel"

    val userid = MutableLiveData<String>()
    val userName = MutableLiveData<String>()
    val userPhotoUrl = MutableLiveData<String>()
    val userEmail = MutableLiveData<String>()

    private val _serviece_userInformation = MutableLiveData<List<Users>>()
    val serviece_userInformation: LiveData<List<Users>>
        get() = _serviece_userInformation

    private val userData = mutableListOf<Users>()

    private val _userClassList = MutableLiveData<List<ClassList>>()
    val userClassList: LiveData<List<ClassList>>
        get() = _userClassList





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
                        _serviece_userInformation.value = userData
                        Log.d(TAG, "userData $userData")
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
    }


    fun uidfileChecker(uid: String) {
        val filedUser = serviece_userInformation.value?.let {
            it.filter { users ->
                users.uid == uid
            }
        }
        if (filedUser.isNullOrEmpty()) {
            Log.d(TAG, "filedUser ${filedUser.toString()}")
        } else {
            Log.d(TAG, "filedUser123 $filedUser")
            _userClassList.value = filedUser[0].classList
            Log.d(TAG, "userClassList value = ${userClassList.value}")

        }
    }



    // Add a new document with a generated ID

//    fun firebase() {
//
////       val db = FirebaseFirestore.getInstance()
////       val user = HashMap<Any, Any>()
////       user.put("title", "補習班公告")
////       user.put("context", "有問題沒老師問的同學有福拉~\n" +
////               "主任決定在下午自習時間安排多個解題老師\n" +
////               "保證每個學生的問題都能得到解決\n" +
////               "讓各位同學在碰到難題時不再慌張\n" +
////               "解題老師時段為每天的下午6:30~9:30，請多加利用")
////       user.put("time",Timestamp.now())
////
////       db.collection("notice")
////           .add(user)
////           .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference ->
////               Log.d(
////                   TAG, "DocumentSnapshot added with ID: " + documentReference.id
////               )
////           })
////           .addOnFailureListener(OnFailureListener { e -> Log.w(TAG, "Error adding document", e) })
//
//
////        db.collection("announcement")
////            .get()
////            .addOnCompleteListener { task ->
////                if (task.isSuccessful) {
////                    for (document in task.result!!) {
////                        Log.d(TAG, document.id + " => " + document.data)
////                    }
////                } else {
////                    Log.w(TAG, "Error getting documents.", task.exception)
////                }
////            }
//    }
}
