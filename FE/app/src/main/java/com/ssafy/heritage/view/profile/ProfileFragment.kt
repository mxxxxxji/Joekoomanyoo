package com.ssafy.heritage.view.profile

import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.KeywordListAdapter
import com.ssafy.heritage.adpter.SettingListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentProfileBinding
import com.ssafy.heritage.listener.RecyclerItemClickListener

private const val TAG = "ProfileFragment___"

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val settingListAdapter: SettingListAdapter by lazy { SettingListAdapter() }
    private val keywordListAdapter: KeywordListAdapter by lazy { KeywordListAdapter() }

    override fun init() {

        initAdapter()

        initObserver()
    }

    private fun initAdapter() = with(binding) {

        // 설정 메뉴 리스트
        recyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = settingListAdapter

            settingListAdapter.recyclerItemClickListener = object : RecyclerItemClickListener {
                override fun onClick(position: Int) {
                    val name = settingListAdapter.currentList[position]
                    when (name) {
                        /*
                        아이템에 따라 분기처리
                         */
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

    val settingList = arrayListOf<String>("내 여행 분석", "일정 관리", "스크랩", "회원정보 수정", "로그아웃", "회원탈퇴")

    /*
    더미 데이터
     */
    val keyowrd_list = arrayListOf<String>("운전가능", "강한 뚜벅이", "수다쟁이", "수다쟁이", "문화초보")
}