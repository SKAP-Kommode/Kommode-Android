package no.skapfhs.kommode.calender_frag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekDayBinder
import no.skapfhs.kommode.R
import no.skapfhs.kommode.databinding.FragmentCalendarBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val b get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)

        class DayViewContainer(view: View) : ViewContainer(view) {
            val textView = view.findViewById<TextView>(R.id.calendarDayText)
            // Will be set when this container is bound
            lateinit var day: WeekDay

            init {
                view.setOnClickListener {
                    // Use the CalendarDay associated with this container.
                    view.setBackgroundColor(resources.getColor(R.color.white, null))
                }
            }
        }

        b.calendarView.dayBinder = object : WeekDayBinder<DayViewContainer> {
            override fun bind(container: DayViewContainer, data: WeekDay) {
                container.day = data

                container.textView.text = data.date.dayOfMonth.toString()
            }

            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

        }

        val currentDay = LocalDate.now()
        val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
        b.calendarView.setup(currentDay, currentDay, firstDayOfWeek)
        b.calendarView.scrollToDate(currentDay)

        val daysOfWeek = daysOfWeek()
        val titlesContainer = b.titlesContainer.root
        titlesContainer.children
            .map { it as TextView }
            .forEachIndexed { index, textView ->
                val dayOfWeek = daysOfWeek[index]
                val title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                textView.text = title
            }

        return b.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView = view.findViewById<TextView>(R.id.calendarDayText)

    // With ViewBinding
    // val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
}