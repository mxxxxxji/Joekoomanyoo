package com.ssafy.heritage.view.feed

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.heritage.ApplicationClass
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

//    private fun setChip() = with(binding) {
//        chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
//            when (checkedIds[0]) {
//                R.id.tv_feed_my -> {
//                    selectedChip = 1
////                    val newList = dataList.filter { it.userSeq == userSeq }
////                    feedAdapter.submitList(newList)
//                    feedViewModel.getMyFeedList()
////                    strokeFeedAll.visibility = View.GONE
////                    strokeFeedMy.visibility = View.VISIBLE
////                    tvFeedAll.setTextColor(Color.parseColor("#8e8e93"))
////                    tvFeedMy.setTextColor(Color.parseColor("@color/main_color"))
//                    Log.d(TAG, "setChip: 나의 피드")
//                }
//                else -> {
////                    val newList = dataList.filter { true }
////                    feedAdapter.submitList(newList)
//                    feedViewModel.getFeedListAll()
////                    strokeFeedAll.visibility = View.VISIBLE
////                    strokeFeedMy.visibility = View.GONE
////                    tvFeedAll.setTextColor(Color.parseColor("@color/main_color"))
////                    tvFeedMy.setTextColor(Color.parseColor("#8e8e93"))
//                    Log.d(TAG, "setChip: 모두의 피드")
//                }
//            }
//        }
//    }

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