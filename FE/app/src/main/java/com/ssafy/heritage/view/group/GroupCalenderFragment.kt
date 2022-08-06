package com.ssafy.heritage.view.group

import com.events.calendar.utils.EventsCalendarUtil.MULTIPLE_SELECTION
import com.events.calendar.utils.EventsCalendarUtil.SINGLE_SELECTION
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

    override fun onDayLongPressed(selectedDate: Calendar?) {

    }

    override fun onDaySelected(selectedDate: Calendar?) {
        Log.e("CLICKED", EventsCalendarUtil.getDateString(selectedDate, EventsCalendarUtil.DD_MM_YYYY))
        selected.text = getDateString(selectedDate?.timeInMillis)
    }

    override fun onMonthChanged(monthStartDate: Calendar?) {

    }
}