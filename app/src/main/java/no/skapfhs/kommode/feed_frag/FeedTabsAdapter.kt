package no.skapfhs.kommode.feed_frag

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import no.skapfhs.kommode.MainActivity
import no.skapfhs.kommode.MainActivityViewModel
import no.skapfhs.kommode.R


internal class FeedTabsAdapter(private val fa: FragmentActivity) :
    FragmentStateAdapter(fa) {
    // Array of images
    // Adding images from drawable folder
    private val images = intArrayOf(
        R.drawable.outline_feed_24,
        R.drawable.outline_person_24
    )

/*    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.feed_holder, parent, false)
        return ViewHolder(view)
    }

    // This method binds the screen with the view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview

    }*/

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        val mvM = ViewModelProvider(fa as MainActivity)[MainActivityViewModel::class.java]
        return mvM.fetchedData.value?.size ?: 0 // ikke returner null med en gang, vente på svar?
    }

    override fun createFragment(position: Int): Fragment {
        val mvM = ViewModelProvider(fa as MainActivity)[MainActivityViewModel::class.java]
        return ScreenSlidePageFragment(mvM.fetchedData.value!!.elementAt(position))
    }


}
// https://developer.android.com/guide/navigation/navigation-swipe-view-2
