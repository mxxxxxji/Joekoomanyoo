package com.ssafy.heritage.view.group

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.MemberAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Member
import com.ssafy.heritage.data.remote.request.GroupJoin
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.databinding.FragmentGroupDetailBinding

import com.ssafy.heritage.listener.MemberClickListener
import com.ssafy.heritage.view.dialog.ApplyGroupJoinDialog
import com.ssafy.heritage.view.dialog.ApplyGroupJoinDialogInterface
import com.ssafy.heritage.view.dialog.OtherProfileDialog
import com.ssafy.heritage.view.dialog.OtherProfileDialogInterface
import com.ssafy.heritage.viewmodel.GroupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = " GroupDetailFragment___"

class GroupDetailFragment :
    BaseFragment<FragmentGroupDetailBinding>(R.layout.fragment_group_detail),
    ApplyGroupJoinDialogInterface, OtherProfileDialogInterface {

    private val groupViewModel by activityViewModels<GroupViewModel>()
    private val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()
    private val memberAdapter: MemberAdapter by lazy { MemberAdapter() }
    private val applicantAdapter: MemberAdapter by lazy { MemberAdapter() }
    private lateinit var groupInfo: GroupListResponse

    override fun init() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.groupVM = groupViewModel
            Log.d(TAG, "init: ${groupViewModel.groupPermission.value}")
            initAdapter()
            initObserver()
            initClickListener()
        }
    }

    private fun initAdapter() {
        binding.recyclerviewMembers.apply {
            adapter = memberAdapter
            memberAdapter.memberClickListener = object : MemberClickListener {
                override fun onClick(position: Int, member: Member, view: View) {
                    Log.d(TAG, "initAdapter : ${groupViewModel.groupPermission.value!!}")
                    Log.d(TAG, "initAdapter : ${member.toString()}")
                    val dialog = OtherProfileDialog(
                        requireContext(),
                        member,
                        userPermission = groupViewModel.groupPermission.value!!,
                        this@GroupDetailFragment
                    )
                    dialog.show()
                }
            }
        }
        binding.recyclerviewApplicant.apply {
            adapter = applicantAdapter
            applicantAdapter.memberClickListener = object : MemberClickListener {
                override fun onClick(position: Int, member: Member, view: View) {
                    Log.d(TAG, "initAdapter : ${groupViewModel.groupPermission.value!!}")
                    val dialog = OtherProfileDialog(
                        requireContext(),
                        member,
                        userPermission = groupViewModel.groupPermission.value!!,
                        this@GroupDetailFragment
                    )
                    dialog.show()
                }

            }
        }
    }

    private fun initObserver() = with(binding) {
        groupViewModel.groupPermission.observe(viewLifecycleOwner) {
            // ????????? ??????
//            headerApplicant.visibility = View.GONE
//            recyclerviewApplicant.visibility = View.GONE
        }
        groupViewModel.groupMemberList.observe(viewLifecycleOwner) {
            Log.d(TAG, "initObserver groupMemberList: $it")
            var applicantList = mutableListOf<Member>()
            var memberList = mutableListOf<Member>()
            for (i in it) {
                // ???????????? ??????
                if (i.memberStatus == 0) {
                    applicantList.add(i)
                }
                // ?????? ?????? ????????? ??????
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

    private fun initClickListener() = with(binding) {

        // ??????
//        binding.btnSetting.setOnClickListener{
//            val action = GroupDetailFragmentDirections.actionGroupDetailFragmentToGroupModifyFragment(groupInfo)
//            findNavController().navigate(action)
//        }
//
//        binding.btnChangeImage.setOnClickListener {
//
//        }

        // ?????? ??????
        binding.btnSubscription.setOnClickListener {
            val dialog = ApplyGroupJoinDialog(requireContext(), this@GroupDetailFragment)
            dialog.show()
            it.visibility = View.GONE
            binding.btnCancellation.visibility = View.VISIBLE
        }

        // ????????????
        binding.btnCancellation.setOnClickListener {
            // ????????? ?????????????????????? ???????????????
            val dialog = SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("????????? ??????????????????????")
                .setConfirmText("???, ?????????????????????")
                .setCancelText("?????????, ????????????!")
                .showCancelButton(true)
                .setCancelClickListener {
                    it.cancel()
                }
                .setConfirmClickListener {
                    groupViewModel.leaveGroupJoin(groupInfo.groupSeq)
                    Toast.makeText(requireContext(), "????????? ??????????????????.", Toast.LENGTH_SHORT).show()
                    groupViewModel.setGroupPermission(3)
                    it.dismissWithAnimation()
                    binding.btnCancellation.visibility = View.GONE
                    binding.btnSubscription.visibility = View.VISIBLE
                }
                .show()
        }

        // ????????????
        binding.btnDrop.setOnClickListener {
            // ????????? ?????????????????????? ???????????????
            SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("?????? ???????????????????")
                .setContentText("????????? ????????? ???????????? ????????????. ????????? ?????? ???????????????????")
                .setConfirmText("???, ?????????????????????")
                .setCancelText("?????????, ????????????!")
                .showCancelButton(true)
                .setCancelClickListener {
                    it.cancel()
                }
                .setConfirmClickListener {
                    groupViewModel.leaveGroupJoin(groupInfo.groupSeq)
                    Toast.makeText(requireActivity(), "????????? ??????????????????.", Toast.LENGTH_SHORT).show()
                    groupViewModel.setGroupPermission(3)
                    it.dismissWithAnimation()
                    findNavController().popBackStack()
                }
                .show()
        }

        // ????????????
        btnGroupStart.setOnClickListener {
            val map = HashMap<String, String>()
            map.put("groupActive", "Y")
            map.put("groupStatus", "O")
            groupViewModel.setGroupStatus(map)

            makeToast("????????? ?????????????????????")
            findNavController().popBackStack()
        }

        // ????????????
        btnGroupStop.setOnClickListener {
            val map = HashMap<String, String>()
            map.put("groupActive", "N")
            map.put("groupStatus", "F")
            groupViewModel.setGroupStatus(map)

            makeToast("????????? ?????????????????????")
            findNavController().popBackStack()
        }

        // ????????????
        btnGroupRemove.setOnClickListener {
            val map = HashMap<String, String>()
            map.put("groupActive", "N")
            map.put("groupStatus", "F")
            groupViewModel.setGroupStatus(map)

            makeToast("????????? ?????????????????????")
            findNavController().popBackStack()
        }
    }

    override fun onOkBtnClicked(appeal: String) {
        groupViewModel.applyGroupJoin(groupInfo.groupSeq, GroupJoin(appeal, userSeq))
        Log.d(TAG, "????????? ??????????????????.")
        Toast.makeText(requireActivity(), "????????? ??????????????????.", Toast.LENGTH_SHORT).show()
        groupViewModel.setGroupPermission(0)
        // ???????????? ?????? ??????
    }

    override fun onApproveBtnClicked(userSeq: Int) {
        groupViewModel.approveGroupJoin(groupInfo.groupSeq, userSeq)
        Log.d(TAG, "????????? ??????????????????")
        Toast.makeText(requireActivity(), "????????? ??????????????????", Toast.LENGTH_SHORT).show()
        // ??? ???????????? ?????? ??????
    }

    override fun onRefuseBtnClicked(userSeq: Int) {
        groupViewModel.leaveGroupJoin(groupInfo.groupSeq)
        Log.d(TAG, "????????? ????????? ??????????????????.")
        Toast.makeText(requireActivity(), "????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show()
        // ??? ???????????? ?????? ??????
    }

    override fun onDropBtnClicked(userSeq: Int) {
        groupViewModel.outGroupJoin(groupInfo.groupSeq, userSeq)
        Toast.makeText(requireActivity(), "${userSeq}??? ?????? ?????????????????????", Toast.LENGTH_SHORT).show()
        // ??? ???????????? ?????? ??????
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }
}
