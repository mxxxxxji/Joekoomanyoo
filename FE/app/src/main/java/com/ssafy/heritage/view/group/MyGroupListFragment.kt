package com.ssafy.heritage.view.group

import android.util.Log
import androidx.fragment.app.viewModels
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.MyGroupListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.remote.response.MyGroupResponse
import com.ssafy.heritage.databinding.FragmentMyGroupListBinding
import com.ssafy.heritage.viewmodel.GroupViewModel

private const val TAG = "MyGroupListFragment___"

class MyGroupListFragment :
    BaseFragment<FragmentMyGroupListBinding>(R.layout.fragment_my_group_list) {

    private val groupViewModel by viewModels<GroupViewModel>()

    private lateinit var lastGroupListAdapter: MyGroupListAdapter
    private lateinit var applyGroupListAdapter: MyGroupListAdapter
    private lateinit var joinGroupListAdapter: MyGroupListAdapter

    val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()

    override fun init() {
        groupViewModel.selectMyGroups()
        initAdapter()
        initObserver()
        initClickListener()
    }

    private fun initAdapter() {
        lastGroupListAdapter = MyGroupListAdapter()
        applyGroupListAdapter = MyGroupListAdapter()
        joinGroupListAdapter = MyGroupListAdapter()
        binding.recyclerviewLastGroup.adapter = lastGroupListAdapter
        binding.recyclerviewApplyGroup.adapter = applyGroupListAdapter
        binding.recyclerviewJoinGroup.adapter = joinGroupListAdapter
    }

    private fun initObserver() {
        groupViewModel.myGroupList.observe(viewLifecycleOwner) {
            var lastGroupList = mutableListOf<MyGroupResponse>()
            var applyGroupList = mutableListOf<MyGroupResponse>()
            var joinGroupList = mutableListOf<MyGroupResponse>()
            for(i in it){
                // 끝난 스터디
                if(i.groupStatus == 'F'){
                    Log.d(TAG, "F : $i")
                    lastGroupList.add(i)
                }
                // 신청중
                if(i.memberStatus == 0){
                    Log.d(TAG, "진행중 : $i")
                    applyGroupList.add(i)
                }
                // 진행중
                if(i.memberStatus == 1 || i.memberStatus == 2) {
                    Log.d(TAG, "진행중 : $i")
                    joinGroupList.add(i)
                }
            }

            Log.d(TAG, lastGroupList.size.toString())
            Log.d(TAG, applyGroupList.size.toString())
            Log.d(TAG, joinGroupList.size.toString())

            lastGroupListAdapter.submitList(lastGroupList)
            applyGroupListAdapter.submitList(applyGroupList)
            joinGroupListAdapter.submitList(joinGroupList)
        }
    }
    private fun initClickListener() {
//        binding.foldingCell.setOnClickListener {
//            binding.foldingCell.toggle(false)
//        }
    }


}