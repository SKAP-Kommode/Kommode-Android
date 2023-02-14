package no.skapfhs.kommode.feed_frag

import android.content.Context
import android.os.Bundle
import android.text.Spanned
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import io.noties.markwon.Markwon
import io.noties.markwon.SoftBreakAddsNewLinePlugin
import io.noties.markwon.image.ImagesPlugin
import no.skapfhs.kommode.MainActivity
import no.skapfhs.kommode.MainActivityViewModel
import no.skapfhs.kommode.R
import org.commonmark.node.Node

class ScreenSlidePageFragment() : Fragment() {

    private var feedID = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("SUBFEED Created","TRUE")
        val mvM = ViewModelProvider(activity as MainActivity)[MainActivityViewModel::class.java]
        val vM: FeedFragmentViewModel by viewModels()
        val pos = requireArguments().getInt("pos");
        val feedDoc = mvM.fetchedData.value!!.elementAt(pos)

        val root = inflater.inflate(R.layout.fragment_subfeed, container, false)
        val feedRecyclerView = root.findViewById<RecyclerView>(R.id.feed_content)

        // POSSIBLE RACE CRASH, CHECK OUT. IF mvM.fetchedData observed before vM.feed
        vM.pullFeed(feedDoc)

        // TODO: Swapping the whole adapter can't be the most efficient way...
        vM.feedData.observe(viewLifecycleOwner) {
            var feedContent = arrayListOf<FeedContentViewHolder>()
            for (item in it) {
                Log.d("FIREDEBUGe", item.data!!["content"] as String)
                // TODO: add checks for content?
                feedContent.add(FeedContentViewHolder(item.data!!["content"] as String))
            }
            Log.d("FIRERER", feedContent.toString())

            //if (feedRecyclerView.adapter == null) {
                feedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                feedRecyclerView.adapter = FeedContentAdapter(feedContent, requireContext())
/*            } else {
                Log.d("UPDATEDDDD", "TEST")
                feedRecyclerView.adapter.
                feedRecyclerView.adapter!!.notifyDataSetChanged()
            }*/
        }

        mvM.fetchedData.observe(viewLifecycleOwner) { vM.pullFeed(feedDoc) }

        return root
    }
}

data class FeedContentViewHolder(val content: String)

class FeedContentAdapter(private val feedContentList: List<FeedContentViewHolder>, private val context: Context): RecyclerView.Adapter<FeedContentAdapter.ViewHolder>() {
    // alternativ i fremtiden ? https://medium.com/android-news/smart-way-to-update-recyclerview-using-diffutil-345941a160e0
    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

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

        // obtain an instance of Markwon
        val markwon: Markwon = Markwon.builder(context)
            .usePlugin(SoftBreakAddsNewLinePlugin.create())
            .usePlugin(ImagesPlugin.create())
            .build();

// parse markdown to commonmark-java Node
        val node: Node = markwon.parse(itemsViewModel.content.replace("  ", "\n"));

        Log.d("OUTP",itemsViewModel.content);

// create styled text from parsed Node
        val markdown: Spanned = markwon.render(node);

// use it on a TextView
        markwon.setParsedMarkdown(holder.textView, markdown);

        // sets the text to the textview from our itemHolder class

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return feedContentList.size
    }
}
