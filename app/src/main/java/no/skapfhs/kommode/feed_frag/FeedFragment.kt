package no.skapfhs.kommode.feed_frag

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import no.skapfhs.kommode.MainActivity
import no.skapfhs.kommode.MainActivityViewModel
import no.skapfhs.kommode.databinding.FragmentFeedBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFeedBinding.inflate(inflater, container, false)

        val viewModel: FeedFragmentViewModel by viewModels()
        val viewModelMain: MainActivityViewModel by viewModels()

        val adapter = context?.let { activity?.let { it1 -> FeedTabsAdapter(it1) } }
        binding.feedViewPager.adapter = adapter

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mvM = ViewModelProvider(activity as MainActivity)[MainActivityViewModel::class.java]
        Log.d("TEST","RAn")
        mvM.fetchedData.observe(viewLifecycleOwner) {
            Log.d("TEST",it.toString())
            binding.feedLoadingIndicator.visibility = View.INVISIBLE
            binding.feedMainHolder.visibility = View.VISIBLE

            TabLayoutMediator(binding.feedTabs, binding.feedViewPager) { tab, position ->
                tab.text = it.elementAt(position)["fullname"].toString()
                tab.icon = context?.let { context -> getDrawable(context,android.R.drawable.alert_dark_frame) }
            }.attach()
        }
        //handle change in tabs, and swap main_feed content

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}