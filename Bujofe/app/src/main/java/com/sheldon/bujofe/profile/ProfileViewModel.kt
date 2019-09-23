package com.sheldon.bujofe.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import org.threeten.bp.LocalTime

class ProfileViewModel : ViewModel() {
    private val TAG: String = "viewModel"


    // Add a new document with a generated ID

    fun firebase() {




//       val db = FirebaseFirestore.getInstance()
//       val user = HashMap<Any, Any>()
//       user.put("title", "補習班公告")
//       user.put("context", "有問題沒老師問的同學有福拉~\n" +
//               "主任決定在下午自習時間安排多個解題老師\n" +
//               "保證每個學生的問題都能得到解決\n" +
//               "讓各位同學在碰到難題時不再慌張\n" +
//               "解題老師時段為每天的下午6:30~9:30，請多加利用")
//       user.put("time",Timestamp.now())
//
//       db.collection("notice")
//           .add(user)
//           .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference ->
//               Log.d(
//                   TAG, "DocumentSnapshot added with ID: " + documentReference.id
//               )
//           })
//           .addOnFailureListener(OnFailureListener { e -> Log.w(TAG, "Error adding document", e) })


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
