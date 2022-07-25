package com.ssafy.heritage.view.group

import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.GroupListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentGroupListBinding

class GroupListFragment : BaseFragment<FragmentGroupListBinding>(R.layout.fragment_group_list) {

    private lateinit var adapter: GroupListAdapter

    override fun init() {
        // 현재 모임 정보 불러오기 (최신순)

        initAdapter()
    }
    private fun initAdapter(){
       // adapter = GroupListAdapter()
        binding.apply {
            recyclerviewGroupList.adapter = adapter
        }
    }


}