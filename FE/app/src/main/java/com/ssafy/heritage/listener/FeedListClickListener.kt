package com.ssafy.heritage.listener

import android.view.View
import com.ssafy.heritage.data.dto.Feed

interface FeedListClickListener {
    fun onClick(position: Int, feed: Feed, view: View)
}