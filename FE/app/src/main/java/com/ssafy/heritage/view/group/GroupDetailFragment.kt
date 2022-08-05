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
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.databinding.FragmentGroupDetailBinding
import com.ssafy.heritage.viewmodel.GroupViewModel
import com.ssafy.heritage.viewmodel.UserViewModel

private const val TAG = " GroupDetailFragment___"

class GroupDetailFragment :
    BaseFragment<FragmentGroupDetailBinding>(R.layout.fragment_group_detail),
    OnItemClickListener {

    private val userViewModel by activityViewModels<UserViewModel>()
    private val groupViewModel by activityViewModels<GroupViewModel>()
    private lateinit var user:User
        //User(0, "ssafy@naver.com", "블랙맘바", "1", "970317", "N", 'W', "", "", "", "", 'N')
    private lateinit var memberAdapter: MemberAdapter
    private lateinit var applicantAdapter: MemberAdapter


    override fun init() {
        binding.groupVM = groupViewModel
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
        userViewModel.user.observe(viewLifecycleOwner) {
            user = it
        }
        groupViewModel.groupPermission.observe(viewLifecycleOwner) {
            // 신청자 목록
//            headerApplicant.visibility = View.GONE
//            recyclerviewApplicant.visibility = View.GONE
        }
        groupViewModel.groupMemberList.observe(viewLifecycleOwner) {

            Log.d(TAG, "groupMemberList")
            Log.d(TAG, it.toString())
            memberAdapter.submitList(it)
            applicantAdapter.submitList(it)
        }
        groupViewModel.detailInfo.observe(viewLifecycleOwner) {
            Log.d(TAG, "detailInfo")
            Log.d("master", it.master)
            Log.d("name", it.name)
            groupDetailInfo = it

            when(it.status){
                'R' -> {
                    Log.d(TAG, "GROUP IS RECRUITING")
                    constraintBtn.visibility = View.VISIBLE
                }
                'O' -> {
                    Log.d(TAG, "GROUP IS OPENING")
                    constraintBtn.visibility = View.GONE
                }
                'F' -> {
                    Log.d(TAG, "GROUP IS FINISHED")
                   constraintBtn.visibility = View.GONE
                }
            }

        }
    }

    override fun onItemClick(position: Int) {

    }
}