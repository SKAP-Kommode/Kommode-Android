package no.skapfhs.kommode.feed_frag

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.model.Document
import com.google.firebase.firestore.model.FieldPath
import no.skapfhs.kommode.R

class ScreenSlidePageFragment(private val feed_doc: DocumentSnapshot) : Fragment() {

    private var feedID = ""

    init {
        feedID = feed_doc.id
        Log.d("SUBFEED",feed_doc.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root = inflater.inflate(R.layout.fragment_subfeed, container, false)
        val testText = root.findViewById<RecyclerView>(R.id.feed_content)

        val feedContent = arrayListOf<feedContentViewModel>()

        feed_doc.reference.collection("items").get().addOnSuccessListener {

            for (item in it.documents) {
                Log.d("FIREDEBUGe",item.data.toString())
                // TODO: add checks for content?
                feedContent.add(feedContentViewModel(item.data!!["content"] as String))
            }

            testText.adapter = FeedContentAdapter(feedContent)

        }

        val viewModel: FeedFragmentViewModel by viewModels()
        viewModel.interperetFeedData(feed_doc, requireContext())

        return root
    }
}

data class feedContentViewModel(val content: String)

class FeedContentAdapter(private val feedContentList: List<feedContentViewModel>): RecyclerView.Adapter<FeedContentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.feedcard_markdown, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = feedContentList[position]

        // sets the text to the textview from our itemHolder class
        holder.textView.text = itemsViewModel.content

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return feedContentList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}
