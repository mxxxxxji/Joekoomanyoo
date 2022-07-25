package com.ssafy.heritage.view.heritage

import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.HeritageListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentHeritageListBinding

private const val TAG = "HeritageListFragment___"

class HeritageListFragment :
    BaseFragment<FragmentHeritageListBinding>(R.layout.fragment_heritage_list) {

    private val heritageAdapter: HeritageListAdapter by lazy { HeritageListAdapter() }

    override fun init() {

        initAdapter()
    }

    private fun initAdapter() = with(binding) {
        recyclerview.adapter = heritageAdapter

        // heritageAdapter.submitList(리스트)  // submitList로 리스트 넣어줌
    }
}