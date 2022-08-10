package com.ssafy.heritage.view.feed

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.adpter.FeedListAdapter
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.OnItemClickListener
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Feed
import com.ssafy.heritage.databinding.FragmentFeedListBinding
import com.ssafy.heritage.viewmodel.FeedViewModel

private const val TAG = "FeedListFragment___"

class FeedListFragment :
    BaseFragment<FragmentFeedListBinding>(R.layout.fragment_feed_list), OnItemClickListener {

    private lateinit var feedAdapter: FeedListAdapter
    private val feedViewModel by activityViewModels<FeedViewModel>()
    val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()
    private var clickMyList: Int = 0
    private var searchedList = listOf<Feed>()


    override fun init() {

        feedViewModel.getFeedListAll()
        feedViewModel.
        initAdapter()
        initObserver()
        initClickListener()
    }

    private fun initAdapter() {
        feedAdapter = FeedListAdapter(this)
        binding.recyclerviewFeedList.adapter = feedAdapter
        binding.recyclerviewFeedList.layoutManager = GridLayoutManager(requireContext(),3)
        feedAdapter.feed

    }

    private fun initObserver() {
        feedViewModel.feedListAll.observe(viewLifecycleOwner) {
            Log.d(TAG, "initObserver feedListAll: $it")
            feedAdapter.submitList(it)
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

        tvFeedAll.setOnClickListener {
            feedViewModel.getFeedListAll()
        }
    }

//    override fun onItemClick(position: Int) {
//        Log.d(TAG, "onItemClick: $position")
//        val action = FeedListFragmentDirections.actionFeedListFragmentToFeedDetailFragment(feedAdapter.getItem(position))
//        findNavController().navigate(action)
//
//    }

}