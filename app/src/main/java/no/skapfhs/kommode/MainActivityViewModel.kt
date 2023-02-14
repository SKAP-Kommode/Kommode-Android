package no.skapfhs.kommode

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class MainActivityViewModel : ViewModel() {

    // Expose screen UI state
    //private val _fetchedData: MutableLiveData<QuerySnapshot?> = MutableLiveData(null)
    var fetchedData: MutableLiveData<List<DocumentSnapshot>> =  MutableLiveData()

    // Handle business logic
    fun getFeeds(): Task<QuerySnapshot> {
        val repo = KommodeRepo()
        val fetch = repo.fetchFeeds()
        fetch.addOnSuccessListener { snapshot ->
                Log.d("Gotten feed", snapshot.documents.toString())
                fetchedData.value = snapshot.documents
            }
        return fetch
    }
}
