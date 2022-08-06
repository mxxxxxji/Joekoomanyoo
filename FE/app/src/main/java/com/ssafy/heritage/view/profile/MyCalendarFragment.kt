package com.ssafy.heritage.view.profile

import android.util.Log
import com.events.calendar.views.EventsCalendar
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentMyCalendarBinding
import java.util.*

private const val TAG = "MyCalendarFragment___"

class MyCalendarFragment : BaseFragment<FragmentMyCalendarBinding>(R.layout.fragment_my_calendar),
    EventsCalendar.Callback {
    override fun init() {

        val today = Calendar.getInstance()
        val end = Calendar.getInstance()
        end.add(Calendar.YEAR, 2)
        val start = Calendar.getInstance()
        start.add(Calendar.YEAR, -10)
        binding.calendar.setSelectionMode(binding.calendar.SINGLE_SELECTION)
            .setToday(today)
            .setMonthRange(start, end)
            .setWeekStartDay(Calendar.SUNDAY, false)
            .setIsBoldTextOnSelectionEnabled(true)
            .setCallback(this)
            .build()
    }

    override fun onDayLongPressed(selectedDate: Calendar?) {
        Log.d(TAG, "onDayLongPressed: ")
    }

    override fun onDaySelected(selectedDate: Calendar?) {
        Log.d(TAG, "onDaySelected: ")
    }

    override fun onMonthChanged(monthStartDate: Calendar?) {
        Log.d(TAG, "onMonthChanged: ")
    }
}