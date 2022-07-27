package com.ssafy.heritage.view.group

import androidx.fragment.app.viewModels
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.GroupListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentGroupListBinding
import com.ssafy.heritage.viewmodel.GroupViewModel

private const val TAG = "GroupListFragment___"

class GroupListFragment :
    BaseFragment<FragmentGroupListBinding>(R.layout.fragment_group_list) {

    private val groupListAdapter: GroupListAdapter by lazy { GroupListAdapter() }
    private val groupViewModel by viewModels<GroupViewModel>()

    override fun init() {
        groupViewModel.getGroupList()
        initAdapter()
        initObserver()
    }
    private fun initAdapter() = with(binding) {
        recyclerviewGroupList.adapter = groupListAdapter
    }

    private fun initObserver(){
        groupViewModel.groupList.observe(viewLifecycleOwner){
            groupListAdapter.submitList(it)
        }
    }

}