package no.skapfhs.kommode.feed_frag
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class FeedFragmentViewModel: ViewModel() {
    var feedData = MutableLiveData<List<DocumentSnapshot>>()

    // Lage funksjon for henting av forskjellige feed IDer og lagring i LiveData, som oppdateres hvis feed_data i Main vM oppdateres

    fun pullFeed(feedDoc: DocumentSnapshot): Task<QuerySnapshot> {
        return feedDoc.reference.collection("items").get()
            .addOnSuccessListener {
                feedData.value = it.documents
            }
    }

}