package com.ssafy.heritage.view.profile

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.KeywordListAdapter
import com.ssafy.heritage.adpter.SettingListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentProfileBinding
import com.ssafy.heritage.listener.SettingListClickListener
import com.ssafy.heritage.util.ProfileSetting.LOGOUT
import com.ssafy.heritage.util.ProfileSetting.MODIFY_PROFILE
import com.ssafy.heritage.util.ProfileSetting.MY_TRIP
import com.ssafy.heritage.util.ProfileSetting.SCHEDULE
import com.ssafy.heritage.util.ProfileSetting.SCRAP
import com.ssafy.heritage.util.ProfileSetting.SIGNOUT

private const val TAG = "ProfileFragment___"

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val settingListAdapter: SettingListAdapter by lazy { SettingListAdapter() }
    private val keywordListAdapter: KeywordListAdapter by lazy { KeywordListAdapter() }

    override fun init() {

        initAdapter()

        initObserver()

        initClickListener()
    }

    private fun initAdapter() = with(binding) {

        // 설정 메뉴 리스트
        recyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = settingListAdapter

            settingListAdapter.settingListClickListener = object : SettingListClickListener {
                override fun onClick(position: Int, view: View) {
                    val name = settingListAdapter.currentList[position]
                    when (name) {
                        MY_TRIP -> {

                        }
                        SCHEDULE -> {

                        }
                        SCRAP -> {

                        }
                        MODIFY_PROFILE -> {

                        }
                        LOGOUT -> {

                        }
                        SIGNOUT -> {

                        }
                    }
                }
            }

            settingListAdapter.submitList(settingList)
        }

        // 키워드 리스트
        recyclerviewKeyword.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = keywordListAdapter

            keywordListAdapter.submitList(keyowrd_list)
        }

    }

    private fun initObserver() {
        /*
        키워드 목록 받아와야함
         */
    }

    private fun initClickListener() = with(binding) {

        // 설정 아이콘 클릭시
        btnSetting.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_settingFragment)
        }
    }

    val settingList = arrayListOf<String>(MY_TRIP, SCHEDULE, SCRAP, MODIFY_PROFILE, LOGOUT, SIGNOUT)

    /*
    더미 데이터
     */
    val keyowrd_list = arrayListOf<String>("운전가능", "강한 뚜벅이", "수다쟁이", "수다쟁이", "문화초보")
}