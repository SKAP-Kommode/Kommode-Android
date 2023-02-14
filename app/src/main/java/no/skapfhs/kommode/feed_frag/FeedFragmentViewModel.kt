package no.skapfhs.kommode.feed_frag
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot

data class Feed (
    val name: String? = null
)

data class FetchedFeedData (
    val data: MutableList<Feed> = MutableList(1) {
        Feed()
    }
)

class FeedFragmentViewModel: ViewModel() {
    private val _uiState = MutableLiveData(FetchedFeedData())
    val uiState: FetchedFeedData? = _uiState.value

    // Handle business logic
    fun interperetFeedData(data: DocumentSnapshot, context: Context): List<View> {
        val items = data["items"]
        return listOf<View>(TextView(context))
    }

}