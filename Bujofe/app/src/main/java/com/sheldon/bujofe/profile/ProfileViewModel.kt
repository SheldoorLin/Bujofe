package com.sheldon.bujofe.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class ProfileViewModel : ViewModel() {
    private val TAG: String = "viewModel"


    // Add a new document with a generated ID

   fun firebase() {
        val db = FirebaseFirestore.getInstance()
        val user = HashMap<Any, Any>()
        user.put("title", "颱風公告")
        user.put("context", "因本月有10個颱風:\n" +
                "停課一周\n")
       user.put("time",Timestamp.now())

        db.collection("announcement")
            .add(user)
            .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference ->
                Log.d(
                    TAG,
                    "DocumentSnapshot added with ID: " + documentReference.id
                )
            })
            .addOnFailureListener(OnFailureListener { e -> Log.w(TAG, "Error adding document", e) })
//        db.collection("announcement")
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    for (document in task.result!!) {
//                        Log.d(TAG, document.id + " => " + document.data)
//                    }
//                } else {
//                    Log.w(TAG, "Error getting documents.", task.exception)
//                }
//            }
    }
}
