package com.ssafy.heritage.view.feed

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.adpter.FeedListAdapter
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentFeedListBinding
import com.ssafy.heritage.viewmodel.FeedViewModel

private const val TAG = "FeedListFragment___"

class FeedListFragment :
    BaseFragment<FragmentFeedListBinding>(R.layout.fragment_feed_list) {

    private val feedAdapter: FeedListAdapter by lazy { FeedListAdapter() }
    private val feedViewModel by activityViewModels<FeedViewModel>()
    val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()

    override fun init() {

        feedViewModel.getFeedListAll()
        feedViewModel.getFeedListAll()
        feedViewModel.getMyFeedList()
        initAdapter()
        initObserver()
        initClickListener()
    }

    private fun initAdapter() = with(binding) {
        recyclerview.adapter = feedAdapter

    }

    private fun initObserver() {
    }

    private fun initClickListener() {
        binding.apply {
            fabCreateFeed.setOnClickListener {
                findNavController().navigate(R.id.action_feedListFragment_to_feedCreateFragment)
            }
        }
    }

//    override fun onItemClick(Position: Int) {
//    }

}