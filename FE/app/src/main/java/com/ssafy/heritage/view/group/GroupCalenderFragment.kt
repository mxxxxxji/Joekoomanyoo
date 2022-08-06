package com.ssafy.heritage.view.group

import android.os.Bundle
import android.util.Log
import com.events.calendar.utils.EventsCalendarUtil
import com.events.calendar.utils.EventsCalendarUtil.MULTIPLE_SELECTION
import com.events.calendar.utils.EventsCalendarUtil.SINGLE_SELECTION
import com.events.calendar.utils.EventsCalendarUtil.getDateString
import com.events.calendar.utils.EventsCalendarUtil.today
import com.events.calendar.views.EventsCalendar
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentGroupCalendarBinding
import java.util.*


class GroupCalenderFragment : BaseFragment<FragmentGroupCalendarBinding>(R.layout.fragment_group_calendar), EventsCalendar.Callback {

    override fun init() {
        binding.eventsCalendar.setSelectionMode(SINGLE_SELECTION) //set mode of Calendar
            .setToday(today) //set today's date [today: Calendar]
           // .setMonthRange(start, end) //set starting month [start: Calendar] and ending month [end: Calendar]
            .setWeekStartDay(Calendar.SUNDAY, false) //set start day of the week as you wish [startday: Int, doReset: Boolean]
//            .setCurrentSelectedDate(today) //set current date and scrolls the calendar to the corresponding month of the selected date [today: Calendar]
 //           .setDatesTypeface(typeface) //set font for dates
            .setDateTextFontSize(16f) //set font size for dates
           // .setMonthTitleTypeface(typeface) //set font for title of the calendar
            .setMonthTitleFontSize(16f) //set font size for title of the calendar
           // .setWeekHeaderTypeface(typeface) //set font for week names
            .setWeekHeaderFontSize(16f) //set font size for week names
           // .setCallback(this) //set the callback for EventsCalendar
           // .addEvent(c) //set events on the EventsCalendar [c: Calendar]
           // .disableDate(dc) //disable a specific day on the EventsCalendar [c: Calendar]
            .disableDaysInWeek(Calendar.SATURDAY, Calendar.SUNDAY) //disable days in a week on the whole EventsCalendar [varargs days: Int]
            .build()

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val today = Calendar.getInstance()

        val start = Calendar.getInstance()
        start.add(Calendar.YEAR, -10)

        val end = Calendar.getInstance()
        end.add(Calendar.YEAR, 20)

        binding.eventsCalendar.setSelectionMode(SINGLE_SELECTION)
            .setToday(today)
            .setMonthRange(today, end)
            .setWeekStartDay(Calendar.SUNDAY, false)
//            .setDatesTypeface(FontsManager.getTypeface(FontsManager.OPENSANS_REGULAR, requireContext()))
//            .setMonthTitleTypeface(FontsManager.getTypeface(FontsManager.OPENSANS_SEMIBOLD, requireContext()))
//            .setWeekHeaderTypeface(FontsManager.getTypeface(FontsManager.OPENSANS_SEMIBOLD, requireContext()))
            .setCallback(this)
            .build()

        binding.eventsCalendar.post {
            binding.eventsCalendar.setCurrentSelectedDate(today)
        }

        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_MONTH, 2)
        binding.eventsCalendar.addEvent(c)
        c.add(Calendar.DAY_OF_MONTH, 3)
        binding.eventsCalendar.addEvent(c)
        c.add(Calendar.DAY_OF_MONTH, 4)
        binding.eventsCalendar.addEvent(c)
        c.add(Calendar.DAY_OF_MONTH, 7)
        binding.eventsCalendar.addEvent(c)
    }
    override fun onDayLongPressed(selectedDate: Calendar?) {

    }

    override fun onDaySelected(selectedDate: Calendar?) {
        Log.e("CLICKED", EventsCalendarUtil.getDateString(selectedDate, EventsCalendarUtil.DD_MM_YYYY))
        binding.selected.text = getDateString(selectedDate?.timeInMillis)
    }

    override fun onMonthChanged(monthStartDate: Calendar?) {

    }
    private fun getDateString(time: Long?): String {
        if (time != null) {
            val cal = Calendar.getInstance()
            cal.timeInMillis = time
            val month = when (cal[Calendar.MONTH]) {
                Calendar.JANUARY -> "January"
                Calendar.FEBRUARY -> "February"
                Calendar.MARCH -> "March"
                Calendar.APRIL -> "April"
                Calendar.MAY -> "May"
                Calendar.JUNE -> "June"
                Calendar.JULY -> "July"
                Calendar.AUGUST -> "August"
                Calendar.SEPTEMBER -> "September"
                Calendar.OCTOBER -> "October"
                Calendar.NOVEMBER -> "November"
                Calendar.DECEMBER -> "December"
                else -> ""
            }
            return "$month ${cal[Calendar.DAY_OF_MONTH]}, ${cal[Calendar.YEAR]}"
        } else return ""
    }
}