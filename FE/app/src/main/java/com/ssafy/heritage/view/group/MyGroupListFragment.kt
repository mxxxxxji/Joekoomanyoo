package com.ssafy.heritage.view.group

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.GroupMyListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentMyGroupListBinding
import com.ssafy.heritage.viewmodel.GroupViewModel

private const val TAG = "MyGroupListFragment___"

class MyGroupListFragment :
    BaseFragment<FragmentMyGroupListBinding>(R.layout.fragment_my_group_list) {

    private val groupViewModel by activityViewModels<GroupViewModel>()

    val lastGroupListAdapter: GroupMyListAdapter by lazy { GroupMyListAdapter() }
    val applyGroupListAdapter: GroupMyListAdapter by lazy { GroupMyListAdapter() }
    val joinGroupListAdapter: GroupMyListAdapter by lazy { GroupMyListAdapter() }

    val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()

    override fun init() {
        groupViewModel.selectMyGroups()
        initAdapter()
        initObserver()
    }

    private fun initAdapter() = with(binding) {
        recyclerviewLastGroup.apply {
            adapter = lastGroupListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        recyclerviewApplyGroup.apply {
            adapter = applyGroupListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        recyclerviewJoinGroup.apply {
            adapter = joinGroupListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        }
    }

    private fun initObserver() {
        groupViewModel.myGroupList.observe(viewLifecycleOwner) {
            val lastGroupList = it.filter { it.groupStatus == 'F' }
            val applyGroupList = it.filter { it.groupStatus != 'F' && it.memberStatus == 0 }
            val joinGroupList = it.filter { it.groupStatus != 'F' && it.memberStatus != 0 }

            Log.d(TAG, lastGroupList.size.toString())
            Log.d(TAG, applyGroupList.size.toString())
            Log.d(TAG, joinGroupList.size.toString())

            lastGroupListAdapter.submitList(lastGroupList)
            applyGroupListAdapter.submitList(applyGroupList)
            joinGroupListAdapter.submitList(joinGroupList)
        }
    }
}