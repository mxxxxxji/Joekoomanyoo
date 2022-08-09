package com.ssafy.heritage.view.feed

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.adpter.FeedListAdapter
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Feed
import com.ssafy.heritage.databinding.FragmentFeedListBinding
import com.ssafy.heritage.viewmodel.FeedViewModel

private const val TAG = "FeedListFragment___"

class FeedListFragment :
    BaseFragment<FragmentFeedListBinding>(R.layout.fragment_feed_list) {

    private val feedAdapter: FeedListAdapter by lazy { FeedListAdapter() }
    private val feedViewModel by activityViewModels<FeedViewModel>()
    val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()
    private var feedList: List<Feed> = arrayListOf()
    private var myList: List<Feed> = arrayListOf()
    private var clickMyList: Int = 0
    private var searchedList = listOf<Feed>()


    override fun init() {

        initAdapter()
        initObserver()
        initClickListener()
    }

    private fun initAdapter() = with(binding) {
//        recyclerview.adapter = feedAdapter
        recyclerview.apply {
//            feedAdapter.feedListClickListener = object : FeedListAdapter {
//                override fun onClick(position: Int, feed: Feed, view: View) {
//                }
//            }
        }
    }

    private fun initObserver() {
        feedViewModel.feedListAll.observe(viewLifecycleOwner) {
            if (searchedList.isNotEmpty()) {
                feedAdapter.submitList(searchedList)
            } else if (clickMyList == 0) {
                feedViewModel.getFeedListAll()
                feedList = it
                feedAdapter.submitList(it)
            } else {
                feedViewModel.getMyFeedList()
            }
            Log.d(TAG, "initObserver: 아이템 뜨니?")
        }
    }

    private fun initClickListener() = with(binding) {
        binding.apply {
            fabCreateFeed.setOnClickListener {
                findNavController().navigate(R.id.action_feedListFragment_to_feedCreateFragment)
            }
        }

        tvFeedMy.setOnClickListener {
            clickMyList = 1
            feedViewModel.getMyFeedList()
        }
    }

//    override fun onItemClick(Position: Int) {
//    }

}