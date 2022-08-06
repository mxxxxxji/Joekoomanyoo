package com.ssafy.heritage.view.profile

import android.util.Log
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.events.calendar.views.EventsCalendar
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.ScheduleListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentMyCalendarBinding
import com.ssafy.heritage.databinding.ItemMyCalendarBinding
import com.ssafy.heritage.util.DividerItemDecoration
import com.ssafy.heritage.viewmodel.UserViewModel
import java.util.*

private const val TAG = "MyCalendarFragment___"

class MyCalendarFragment : BaseFragment<FragmentMyCalendarBinding>(R.layout.fragment_my_calendar),
    EventsCalendar.Callback {

    private val userViewModel by activityViewModels<UserViewModel>()

    override fun init() {

        setCalendar()

        initObserver()

        userViewModel.getSchedule()

        binding.constraintBackground.setOnClickListener {
            if (!binding.etSchedule.isFocused) {
                binding.motionlayout.transitionToStart()
            }
        }
    }

    private fun initObserver() {
        userViewModel.myScheduleList.observe(viewLifecycleOwner) {
            it.forEach {
                val year = it.myScheduleDate.substring(0..3).toInt()
                val month = it.myScheduleDate.substring(4..5).toInt()
                val day = it.myScheduleDate.substring(6..7).toInt()
                Log.d(TAG, "initObserver: ${year} ${month} ${day}")
                val c: Calendar = Calendar.getInstance()
                c.set(year, month - 1, day)
                binding.calendar.addEvent(c)
            }
        }
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
    }

    override fun onDayLongPressed(selectedDate: Calendar?) {
        Log.d(TAG, "onDayLongPressed: ")
    }

    override fun onDaySelected(selectedDate: Calendar?) {
        Log.d(TAG, "onDaySelected: $selectedDate")

        // 프레임에 있는 기존 뷰들 제거
        binding.frame.removeAllViews()

        // 날짜 정보 가져와서 파싱함
        binding.calendar.getCurrentSelectedDate()

        val year = "${selectedDate?.get(Calendar.YEAR)}"
        val month = if (selectedDate?.get(Calendar.MONTH)?.plus(1)!! < 10) {
            "0${selectedDate?.get(Calendar.MONTH)?.plus(1)}"
        } else {
            "${selectedDate?.get(Calendar.MONTH)?.plus(1)}"
        }
        val day = if (selectedDate?.get(Calendar.DAY_OF_MONTH) < 10) {
            "0${selectedDate?.get(Calendar.DAY_OF_MONTH)}"
        } else {
            "${selectedDate?.get(Calendar.DAY_OF_MONTH)}"
        }

        // 리사이클러뷰에 필요한 날짜 데이터
        val date = "$year$month$day"
        Log.d(TAG, "onDaySelected: $date")

        // 프레임 제목에 필요한 날짜 데이터
        val dateString = "${month}월 ${day}일"

        val inflater = LayoutInflater.from(context)
        val cardBinding: ItemMyCalendarBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_my_calendar, binding.frame, true)

        cardBinding.date = dateString
        cardBinding.recyclerView.apply {
            val scheduleAdapter = ScheduleListAdapter()

            adapter = scheduleAdapter

            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            addItemDecoration(DividerItemDecoration(5F, resources.getColor(R.color.link_water)))

            // 선택한 날짜와 리스트에 있는 날짜가 같은 리스트만 뿌려줌
            val list = userViewModel.myScheduleList.value?.filter { it.myScheduleDate == date }
            list?.forEach {
                if (it.myScheduleTime.length < 2) {
                    it.myScheduleTime = "0" + it.myScheduleTime
                }
            }

            val newList = list?.sortedBy { it.myScheduleTime }
            scheduleAdapter.submitList(newList)
        }

        // 프레임 뒤에 클릭 되는걸 방지
        cardBinding.root.setOnClickListener { }

        binding.motionlayout.transitionToEnd()
    }

    override fun onMonthChanged(monthStartDate: Calendar?) {
        Log.d(TAG, "onMonthChanged: ")
    }
}