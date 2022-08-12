package com.ssafy.heritage.view.group

import android.util.Log
import android.view.View
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
import com.ssafy.heritage.listener.MemberClickListener
import com.ssafy.heritage.view.dialog.ApplyGroupJoinDialog
import com.ssafy.heritage.view.dialog.ApplyGroupJoinDialogInterface
import com.ssafy.heritage.view.dialog.OtherProfileDialog
import com.ssafy.heritage.view.dialog.OtherProfileDialogInterface
import com.ssafy.heritage.viewmodel.GroupViewModel
import com.ssafy.heritage.viewmodel.UserViewModel

private const val TAG = " GroupDetailFragment___"

class GroupDetailFragment :
    BaseFragment<FragmentGroupDetailBinding>(R.layout.fragment_group_detail),
    ApplyGroupJoinDialogInterface, OtherProfileDialogInterface {

    private val groupViewModel by activityViewModels<GroupViewModel>()
    private val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()
    private val memberAdapter: MemberAdapter by lazy {MemberAdapter()}
    private val applicantAdapter: MemberAdapter by lazy {MemberAdapter()}
    private lateinit var groupInfo: GroupListResponse

    override fun init() {
        binding.groupVM = groupViewModel
        Log.d(TAG, "init: ${groupViewModel.groupPermission.value}")
       // groupViewModel.selectGroupMembers(userSeq)
        initAdapter()
        initObserver()
        initClickListener()
    }

    private fun initAdapter(){
        binding.recyclerviewMembers.apply {
            adapter = memberAdapter
            memberAdapter.memberClickListener = object : MemberClickListener {
                override fun onClick(position: Int, member: Member, view: View) {
                    Log.d(TAG, "initAdapter : ${groupViewModel.groupPermission.value!!}")
                    val dialog = OtherProfileDialog(requireContext(), member, userPermission= groupViewModel.groupPermission.value!!,this@GroupDetailFragment)
                    dialog.show()
                }
            }
        }
        binding.recyclerviewApplicant.apply {
            adapter = applicantAdapter
            applicantAdapter.memberClickListener = object : MemberClickListener {
                override fun onClick(position: Int, member: Member, view: View) {
                    Log.d(TAG, "initAdapter : ${groupViewModel.groupPermission.value!!}")
                    val dialog = OtherProfileDialog(requireContext(), member,  userPermission= groupViewModel.groupPermission.value!!, this@GroupDetailFragment)
                    dialog.show()
                }

            }
        }
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
            for (i in it) {
                // 신청자일 경우
                if (i.memberStatus == 0) {
                    applicantList.add(i)
                }
                // 회원 또는 방장일 경우
                if (i.memberStatus == 1 || i.memberStatus == 2) {
                    memberList.add(i)
                }
            }
            applicantAdapter.submitList(applicantList)
            memberAdapter.submitList(memberList)

        }
        groupViewModel.detailInfo.observe(viewLifecycleOwner) {
            groupDetailInfo = it
            groupInfo = it
        }
    }

    private fun initClickListener() {

        binding.btnChangeImage.setOnClickListener {

        }

        // 가입 요청
        binding.btnSubscription.setOnClickListener {
            val dialog = ApplyGroupJoinDialog(requireContext(), this)
            dialog.show()
        }

        // 가입취소
        binding.btnCancellation.setOnClickListener {
            // 모임을 떠나시겠습니까? 다이얼로그
            groupViewModel.leaveGroupJoin(groupInfo.groupSeq, userSeq)
            Toast.makeText(requireActivity(), "가입이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            groupViewModel.setGroupPermission(3)
            parentFragment?.findNavController()?.popBackStack()
        }

        // 탈퇴하기
        binding.btnDrop.setOnClickListener {
            // 모임을 떠나시겠습니까? 다이얼로그
            groupViewModel.leaveGroupJoin(groupInfo.groupSeq, userSeq)
            Toast.makeText(requireActivity(), "모임을 탈퇴했습니다.", Toast.LENGTH_SHORT).show()
            groupViewModel.setGroupPermission(3)
            val action =
                GroupDetailFragmentDirections.actionGroupDetailFragmentToGroupListFragment()
            findNavController().navigate(action)
        }

        // 모임시작

        // 모임종료

        // 모임삭제
    }

    override fun onOkBtnClicked(appeal: String) {
        groupViewModel.applyGroupJoin(groupInfo.groupSeq, GroupJoin(appeal, userSeq))
        Log.d(TAG, "가입을 요청했습니다.")
        Toast.makeText(requireActivity(), "가입을 요청했습니다.", Toast.LENGTH_SHORT).show()
        groupViewModel.setGroupPermission(0)
        // 방장에게 알림 전송
    }

    override fun onApproveBtnClicked(userSeq: Int) {
        groupViewModel.approveGroupJoin(groupInfo.groupSeq, GroupBasic(userSeq,groupInfo.groupSeq))
        Log.d(TAG, "가입을 승인했습니다")
        Toast.makeText(requireActivity(), "가입을 승인했습니다", Toast.LENGTH_SHORT).show()
        // 그 회원에게 알림 전송
    }

    override fun onRefuseBtnClicked(userSeq: Int) {
        groupViewModel.leaveGroupJoin(groupInfo.groupSeq, userSeq)
        Log.d(TAG, "가입을 요청을 거절했습니다.")
        Toast.makeText(requireActivity(), "가입을 요청을 거절했습니다.", Toast.LENGTH_SHORT).show()
        // 그 회원에게 알림 전송
    }

    override fun onDropBtnClicked(userSeq: Int) {
        groupViewModel.leaveGroupJoin(groupInfo.groupSeq, userSeq)
        Toast.makeText(requireActivity(), "${groupInfo.groupName}님이 퇴장되었습니다", Toast.LENGTH_SHORT).show()
        // 그 회원에게 알림 전송
    }
}