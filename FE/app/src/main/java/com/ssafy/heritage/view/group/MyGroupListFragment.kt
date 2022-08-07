package com.ssafy.heritage.view.group

import android.util.Log
import androidx.fragment.app.viewModels
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.GroupListAdapter
import com.ssafy.heritage.adpter.MyGroupListAdapter
import com.ssafy.heritage.adpter.OnItemClickListener
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.data.remote.response.MyGroupResponse
import com.ssafy.heritage.databinding.FragmentMyGroupListBinding
import com.ssafy.heritage.viewmodel.GroupViewModel

private const val TAG = "MyGroupListFragment___"

class MyGroupListFragment :
    BaseFragment<FragmentMyGroupListBinding>(R.layout.fragment_my_group_list) {

    private val groupViewModel by viewModels<GroupViewModel>()
    private lateinit var lastGroupListAdapter: MyGroupListAdapter
    val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()

    override fun init() {
        groupViewModel.selectMyGroups(userSeq)
        initAdapter()
        initObserver()
        initClickListener()
    }
    private fun initAdapter() {
        lastGroupListAdapter = MyGroupListAdapter()
        binding.recyclerviewLastGroup.adapter = lastGroupListAdapter
    }
    private fun initObserver() {
        groupViewModel.myGroupList.observe(viewLifecycleOwner) {
            var lastGroupList = mutableListOf<MyGroupResponse>()
            for(i in it){
                if(i.groupStatus == 'F'){
                    Log.d(TAG, it.toString())
                    lastGroupList.add(i)
                }
            }
            Log.d(TAG, lastGroupList.size.toString())
            lastGroupListAdapter.submitList(lastGroupList)
        }

    }
    private fun initClickListener() {
//        binding.foldingCell.setOnClickListener {
//            binding.foldingCell.toggle(false)
//        }
    }


}