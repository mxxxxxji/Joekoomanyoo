package com.ssafy.heritage.view.group

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.events.calendar.utils.EventsCalendarUtil
import com.events.calendar.utils.EventsCalendarUtil.MULTIPLE_SELECTION
import com.events.calendar.utils.EventsCalendarUtil.SINGLE_SELECTION
import com.events.calendar.utils.EventsCalendarUtil.getDateString
import com.events.calendar.utils.EventsCalendarUtil.selectedDate
import com.events.calendar.utils.EventsCalendarUtil.today
import com.events.calendar.views.EventsCalendar
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.remote.request.GroupSchedule
import com.ssafy.heritage.databinding.FragmentGroupCalendarBinding
import com.ssafy.heritage.databinding.ItemMyCalendarBinding
import com.ssafy.heritage.viewmodel.GroupViewModel
import java.util.*


class GroupCalenderFragment : BaseFragment<FragmentGroupCalendarBinding>(R.layout.fragment_group_calendar),
    EventsCalendar.Callback {

    private val groupViewModel by activityViewModels<GroupViewModel>()
    private var cardBinding: ItemMyCalendarBinding? = null
    lateinit var callback: OnBackPressedCallback

    override fun init() {

        setCalendar()

        initObserver()

        initClickListener()

        setTextChangedListener()
//        binding.selected.text = getDateString(binding.eventsCalendar.getCurrentSelectedDate()?.timeInMillis)
//
//        val today = Calendar.getInstance()
//        val end = Calendar.getInstance()
//        end.add(Calendar.YEAR, 2)
//        binding.eventsCalendar.setSelectionMode(binding.eventsCalendar.MULTIPLE_SELECTION)
//            .setToday(today)
//            .setMonthRange(today, end)
//            .setWeekStartDay(Calendar.SUNDAY, false)
//            .setIsBoldTextOnSelectionEnabled(true)
////            .setDatesTypeface(FontsManager.getTypeface(FontsManager.OPENSANS_REGULAR, this))
////            .setMonthTitleTypeface(FontsManager.getTypeface(FontsManager.OPENSANS_SEMIBOLD, this))
////            .setWeekHeaderTypeface(FontsManager.getTypeface(FontsManager.OPENSANS_SEMIBOLD, this))
//            .setCallback(this)
//            .build()
//
//
//        binding.selected.setOnClickListener {
//            val dates = binding.eventsCalendar.getDatesFromSelectedRange()
//            Log.e("SELECTED SIZE", dates.size.toString())
//        }
    }
    private fun initObserver(){
        //groupViewModel.selectGroupSchedule.observe(viewLifecycleOwner){}
        binding.calendar.clearEvents()

        setCalendar()

        // 새로운 일정 등록 시 목록 refresh
        if(cardBinding != null){
            updateScheduleList()
        }
    }

    private fun initClickListener() = with(binding){
        btnAddSchedule.setOnClickListener {
            if(!tilSchedule.editText?.text.isNullOrBlank()) {
//                val schedule = GroupSchedule(
//                    date = groupViewModel.selectGroupScheduleList.value,
//                    content = getCurrentDate()
//
//                )
            }
        }
    }

    private fun setTextChangedListener(){

    }

    private fun setCalendar() {
        val today = Calendar.getInstance()
        val end = Calendar.getInstance()
        end.add(Calendar.YEAR, 20)
        val start = Calendar.getInstance()
        start.add(Calendar.YEAR, -10)
        binding.calendar.setSelectionMode(binding.calendar.SINGLE_SELECTION)
            .setToday(today)
            .setMonthRange(start, end)
            .setWeekStartDay(Calendar.SUNDAY, false)
            .setIsBoldTextOnSelectionEnabled(true)
            .setCallback(this)
            .build()

        //updateEvent()
    }

 //   fun updateEvent() {
//        val it = userViewModel.myScheduleList.value ?: arrayListOf()
//        val it = groupViewModel.selectGroupSchedule.
//        it.forEach {
//            val year = it.myScheduleDate.substring(0..3).toInt()
//            val month = it.myScheduleDate.substring(4..5).toInt()
//            val day = it.myScheduleDate.substring(6..7).toInt()
//            Log.d(com.ssafy.heritage.view.profile.TAG, "initObserver: ${year} ${month} ${day}")
//            val c: Calendar = Calendar.getInstance()
//            c.set(year, month - 1, day)
//            binding.calendar.addEvent(c)
//        }
//    }

    fun updateScheduleList() {}


































    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    override fun onDayLongPressed(selectedDate: Calendar?) {
        Log.e("LONG CLICKED", EventsCalendarUtil.getDateString(selectedDate, EventsCalendarUtil.DD_MM_YYYY))
    }

    override fun onMonthChanged(monthStartDate: Calendar?) {
        Log.e("MON", "CHANGED")
    }

    override fun onDaySelected(selectedDate: Calendar?) {
        Log.e("CLICKED", EventsCalendarUtil.getDateString(selectedDate, EventsCalendarUtil.DD_MM_YYYY))
        //binding.selected.text = getDateString(selectedDate?.timeInMillis)
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