package no.skapfhs.kommode

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class MainActivityViewModel : ViewModel() {

    // Expose screen UI state
    //private val _fetchedData: MutableLiveData<QuerySnapshot?> = MutableLiveData(null)
    var fetchedData: MutableLiveData<List<DocumentSnapshot>> =  MutableLiveData()

    // Handle business logic
    fun getFeeds() {
        val repo = KommodeRepo()
        repo.fetchFeeds()
            .addOnSuccessListener { snapshot ->
                Log.d("WHAT", snapshot.documents.toString())
                fetchedData.value = snapshot.documents
                Log.d("TEST","Got snapshot")
            }

    }
}
