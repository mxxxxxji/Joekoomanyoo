package com.ssafy.heritage.view.group

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.MemberAdapter
import com.ssafy.heritage.adpter.OnItemClickListener
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Member
import com.ssafy.heritage.data.remote.request.GroupBasic
import com.ssafy.heritage.data.remote.request.GroupJoin
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.databinding.FragmentGroupDetailBinding
import com.ssafy.heritage.view.dialog.ApplyGroupJoinDialog
import com.ssafy.heritage.view.dialog.ApplyGroupJoinDialogInterface
import com.ssafy.heritage.viewmodel.GroupViewModel
import com.ssafy.heritage.viewmodel.UserViewModel

private const val TAG = " GroupDetailFragment___"

class GroupDetailFragment :
    BaseFragment<FragmentGroupDetailBinding>(R.layout.fragment_group_detail),
    OnItemClickListener, ApplyGroupJoinDialogInterface {

    private val groupViewModel by activityViewModels<GroupViewModel>()
    private val userSeq : Int = ApplicationClass.sharedPreferencesUtil.getUser()
    private lateinit var memberAdapter: MemberAdapter
    private lateinit var applicantAdapter: MemberAdapter
    private lateinit var groupInfo: GroupListResponse

    override fun init() {
        binding.groupVM = groupViewModel

        initAdapter()
        initObserver()
        initClickListener()
    }

    private fun initAdapter() {
        memberAdapter = MemberAdapter(this)
        applicantAdapter = MemberAdapter(this)
        binding.recyclerviewMembers.adapter = memberAdapter
        binding.recyclerviewApplicant.adapter = applicantAdapter
    }

    private fun initObserver() = with(binding) {
        groupViewModel.groupPermission.observe(viewLifecycleOwner) {
            // 신청자 목록
//            headerApplicant.visibility = View.GONE
//            recyclerviewApplicant.visibility = View.GONE
        }
        groupViewModel.groupMemberList.observe(viewLifecycleOwner) {
            var applicantList = mutableListOf<Member>()
            var memberList = mutableListOf<Member>()
            for(i in it){
                // 신청자일 경우
                if(i.memberStatus == 0){
                    applicantList.add(i)
                }
                // 회원일 경우
                if(i.memberStatus == 1){
                    memberList.add(i)
                }
            }
            applicantAdapter.submitList(applicantList)
            memberAdapter.submitList(memberList)

        }
        groupViewModel.detailInfo.observe(viewLifecycleOwner) {
            groupDetailInfo = it
            groupInfo = it

//            when(it.status){
//                'R' -> {
//                    Log.d(TAG, "GROUP IS RECRUITING")
//                    constraintBtn.visibility = View.VISIBLE
//                }
//                'O' -> {
//                    Log.d(TAG, "GROUP IS OPENING")
//                    constraintBtn.visibility = View.GONE
//                }
//                'F' -> {
//                    Log.d(TAG, "GROUP IS FINISHED")
//                   constraintBtn.visibility = View.GONE
//                }
//            }

        }
    }
    private fun initClickListener() {

        // 가입 요청
        binding.btnSubscription.setOnClickListener {
            val dialog = ApplyGroupJoinDialog(requireContext(), this)
            dialog.show()
        }

        // 가입취소
        binding.btnCancellation.setOnClickListener {
            // 모임을 떠나시겠습니까? 다이얼로그
            groupViewModel.leaveGroupJoin(groupInfo.groupSeq, GroupBasic(groupInfo.groupSeq, userSeq))
            Toast.makeText(requireActivity(), "가입이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            groupViewModel.setGroupPermission(3)
            val action = GroupDetailFragmentDirections.actionGroupDetailFragmentToGroupListFragment()
            findNavController().navigate(action)
        }

        // 탈퇴하기
        binding.btnDrop.setOnClickListener {
            // 모임을 떠나시겠습니까? 다이얼로그
            groupViewModel.leaveGroupJoin(groupInfo.groupSeq, GroupBasic(groupInfo.groupSeq, userSeq))
            Toast.makeText(requireActivity(), "모임을 탈퇴했습니다.", Toast.LENGTH_SHORT).show()
            groupViewModel.setGroupPermission(3)
            val action = GroupDetailFragmentDirections.actionGroupDetailFragmentToGroupListFragment()
            findNavController().navigate(action)
        }
        // 모임시작
        // 모임종료
        // 모임삭제
    }

    override fun onItemClick(position: Int) {
    }

    override fun onOkBtnClicked(appeal: String) {
        groupViewModel.applyGroupJoin(groupInfo.groupSeq, GroupJoin(appeal, userSeq))
        Log.d(TAG, "가입을 요청했습니다.")
        Toast.makeText(requireActivity(), "가입을 요청했습니다.", Toast.LENGTH_SHORT).show()
        groupViewModel.setGroupPermission(0)
    }
}