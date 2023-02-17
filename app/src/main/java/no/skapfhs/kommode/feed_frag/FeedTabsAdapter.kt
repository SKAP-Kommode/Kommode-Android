package no.skapfhs.kommode.feed_frag

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import no.skapfhs.kommode.MainActivity
import no.skapfhs.kommode.MainActivityViewModel
import no.skapfhs.kommode.R


internal class FeedTabsAdapter(private val fa: FragmentActivity) : FragmentStateAdapter(fa) {

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        val mvM = ViewModelProvider(fa as MainActivity)[MainActivityViewModel::class.java]
        return mvM.fetchedData.value?.size ?: 0 // ikke returner null med en gang, vente på svar?
    }

    override fun createFragment(position: Int): Fragment {
        Log.d("FeedTabsAdapter RUN", position.toString())
        val frag = ScreenSlidePageFragment()
        val args = Bundle()
        args.putInt("pos", position)
        frag.arguments = args
        return frag
    }

}
// https://developer.android.com/guide/navigation/navigation-swipe-view-2
