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
        binding.selected.text = getDateString(binding.eventsCalendar.getCurrentSelectedDate()?.timeInMillis)

        val today = Calendar.getInstance()
        val end = Calendar.getInstance()
        end.add(Calendar.YEAR, 2)
        binding.eventsCalendar.setSelectionMode(binding.eventsCalendar.MULTIPLE_SELECTION)
            .setToday(today)
            .setMonthRange(today, end)
            .setWeekStartDay(Calendar.SUNDAY, false)
            .setIsBoldTextOnSelectionEnabled(true)
//            .setDatesTypeface(FontsManager.getTypeface(FontsManager.OPENSANS_REGULAR, this))
//            .setMonthTitleTypeface(FontsManager.getTypeface(FontsManager.OPENSANS_SEMIBOLD, this))
//            .setWeekHeaderTypeface(FontsManager.getTypeface(FontsManager.OPENSANS_SEMIBOLD, this))
            .setCallback(this)
            .build()

        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_MONTH, 2)
        binding.eventsCalendar.addEvent(c)
        c.add(Calendar.DAY_OF_MONTH, 3)
        binding.eventsCalendar.addEvent(c)
        c.add(Calendar.DAY_OF_MONTH, 4)
        binding.eventsCalendar.addEvent(c)
        c.add(Calendar.DAY_OF_MONTH, 7)
        binding.eventsCalendar.addEvent(c)
        c.add(Calendar.MONTH, 1)
        c[Calendar.DAY_OF_MONTH] = 1
        binding.eventsCalendar.addEvent(c)

        binding.selected.setOnClickListener {
            val dates = binding.eventsCalendar.getDatesFromSelectedRange()
            Log.e("SELECTED SIZE", dates.size.toString())
        }



        val dc = Calendar.getInstance()
        dc.add(Calendar.DAY_OF_MONTH, 2)


    }
    override fun onDayLongPressed(selectedDate: Calendar?) {
        Log.e("LONG CLICKED", EventsCalendarUtil.getDateString(selectedDate, EventsCalendarUtil.DD_MM_YYYY))
    }

    override fun onMonthChanged(monthStartDate: Calendar?) {
        Log.e("MON", "CHANGED")
    }

    override fun onDaySelected(selectedDate: Calendar?) {
        Log.e("CLICKED", EventsCalendarUtil.getDateString(selectedDate, EventsCalendarUtil.DD_MM_YYYY))
        binding.selected.text = getDateString(selectedDate?.timeInMillis)
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