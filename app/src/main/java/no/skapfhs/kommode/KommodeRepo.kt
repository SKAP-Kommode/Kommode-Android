package no.skapfhs.kommode

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.json.JSONObject

val db = Firebase.firestore

class KommodeRepo {

    val TAG = "TEST"

    fun fetchFeeds(): Task<QuerySnapshot> {
        val docRef = db.collection("/feeds/")
        return docRef.get()
    }

    fun getPath(path: String): Task<QuerySnapshot> {
        val docRef = db.collection(path)
        return docRef.get()
    }

    fun getLaunderRooms(): Task<QuerySnapshot> {
        val docRef = db.collection("/common/launderrooms/")
        return docRef.get()
    }

}