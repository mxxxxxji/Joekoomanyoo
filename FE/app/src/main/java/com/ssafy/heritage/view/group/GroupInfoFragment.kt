package com.ssafy.heritage.view.group

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentGroupInfoBinding
import com.ssafy.heritage.viewmodel.GroupViewModel

private const val TAG = "GroupInfoFragment___"

class GroupInfoFragment : BaseFragment<FragmentGroupInfoBinding>(R.layout.fragment_group_info) {

    private val args by navArgs<GroupInfoFragmentArgs>()
    private val groupViewModel by activityViewModels<GroupViewModel>()

    override fun init() {
        initObserver()
        groupViewModel.getGroupDetailInfo(args.groupListResponse.groupSeq)
        groupViewModel.selectGroupMembers(args.groupListResponse.groupSeq)
    }

    private fun initObserver(){
        groupViewModel.groupDetailInfo.observe(viewLifecycleOwner){
            binding.apply {

            }
        }
    }
}