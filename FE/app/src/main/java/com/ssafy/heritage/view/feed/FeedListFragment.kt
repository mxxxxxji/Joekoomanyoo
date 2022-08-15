package com.ssafy.heritage.view.feed

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentFeedListBinding

private const val TAG = "FeedListFragment___"

class FeedListFragment :
    BaseFragment<FragmentFeedListBinding>(R.layout.fragment_feed_list) {

    override fun init() {

        initAdapter()

    }

    private fun initAdapter() = with(binding) {

        val adapter = FragmentPagerItemAdapter(
            childFragmentManager, FragmentPagerItems.with(requireContext())
                .add("모두의 피드", FeedListAllFragment()::class.java)
                .add("나의 피드", FeedMyListFragment()::class.java)
                .create()
        )

        viewpager.adapter = adapter
        viewpagertab.setViewPager(viewpager)
    }
}