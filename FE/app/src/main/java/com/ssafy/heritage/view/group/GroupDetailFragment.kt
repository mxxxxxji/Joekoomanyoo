package com.ssafy.heritage.view.group

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.MemberAdapter
import com.ssafy.heritage.adpter.OnItemClickListener
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.User
import com.ssafy.heritage.databinding.FragmentGroupDetailBinding
import com.ssafy.heritage.viewmodel.GroupViewModel

private const val TAG = " GroupDetailFragment___"

class GroupDetailFragment :
    BaseFragment<FragmentGroupDetailBinding>(R.layout.fragment_group_detail),
    OnItemClickListener {


    private val groupViewModel by activityViewModels<GroupViewModel>()
    private val user =
        User(0, "ssafy@naver.com", "블랙맘바", "1", "970317", "N", 'W', "", "", "", "", 'N')
    private lateinit var memberAdapter: MemberAdapter
    private lateinit var applicantAdapter: MemberAdapter
    override fun init() {

        initAdapter()
        initObserver()

    }

    private fun initAdapter() {
        memberAdapter = MemberAdapter(this)
        applicantAdapter = MemberAdapter(this)
        binding.recyclerviewMembers.adapter = memberAdapter
        binding.recyclerviewApplicant.adapter = applicantAdapter
    }

    private fun initObserver() = with(binding) {
        groupViewModel.groupMemberList.observe(viewLifecycleOwner) {
            Log.d(TAG, "groupMemberList")
            Log.d(TAG, it.toString())
            memberAdapter.submitList(it)
            applicantAdapter.submitList(it)
        }
//        groupViewModel.insertGroupInfo.observe(viewLifecycleOwner) {
//            Log.d(TAG, "insertGroupInfo")
//            groupDetailInfo = it
//        }
        groupViewModel.detailInfo.observe(viewLifecycleOwner) {
            Log.d(TAG, "detailInfo")
            groupDetailInfo = it
            Log.d(TAG, it.master)
            Log.d(TAG, it.name)
            // 현재유저가 방장이면
            if (it.master == user.userNickname) {

                Log.d(TAG, "USER IS GROUPMAKER")
                btnSubscription.visibility = View.GONE
                btnCancellation.visibility = View.GONE
                btnDrop.visibility = View.GONE

                if (it.status != 'R') {
                    Log.d(TAG, "GROUP IS NOT RECRUTING")
                    headerApplicant.visibility = View.GONE
                    recyclerviewApplicant.visibility = View.GONE
                }


            } else {
                // 설정, 사진 변경버튼 제거
                Log.d(TAG, "USER IS NOT GROUPMAKER")
                Log.d(TAG, it.toString())
                btnSetting.visibility = View.GONE
                btnChangeImage.visibility = View.GONE
            }

            // 현재유저가 방장이면
            // 그룹상태가 모집중일시 신청자, 구성원이보여야함,
            // 그룹이 시작된 상태면 구성원만

            // 현재유저가 구성원이면,
            // 구성원, 탈퇴하기 버튼

            // 현재유저가 일반인이면,0
            // 구성원,
            // 가입하기 click => 가입 취소버튼
        }
    }

    override fun onItemClick(position: Int) {

    }
}