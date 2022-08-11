package com.ssafy.heritage.view.feed

import android.util.Log
import androidx.fragment.app.activityViewModels
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.FeedListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Feed
import com.ssafy.heritage.data.dto.User
import com.ssafy.heritage.data.remote.response.FeedDetailResponse
import com.ssafy.heritage.data.remote.response.FeedListResponse
import com.ssafy.heritage.databinding.FragmentFeedDetailBinding
import com.ssafy.heritage.viewmodel.FeedViewModel

private const val TAG = "FeedDetailFragment___"

class FeedDetailFragment :
    BaseFragment<FragmentFeedDetailBinding>(R.layout.fragment_feed_detail) {

//    private var feed: Feed? = null
    private var feed: FeedListResponse? = null
    private val feedViewModel by activityViewModels<FeedViewModel>()
    private val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()

    override fun init() {
        binding.feed = feed
    }

    private fun initClickListenr() = with(binding) {
        imagebtnFeedDetailDelete.setOnClickListener {
            feedViewModel.deleteFeed(feed!!.feedSeq)
        }
    }

}