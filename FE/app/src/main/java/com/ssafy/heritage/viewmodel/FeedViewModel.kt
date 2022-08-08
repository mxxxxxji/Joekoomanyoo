package com.ssafy.heritage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.heritage.data.dto.Feed
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "FeedViewModel___"

class FeedViewModel: ViewModel() {

    private val repository = Repository.get()

    private val _myFeedList = SingleLiveEvent<MutableList<Feed>>()
    val myFeedList: LiveData<MutableList<Feed>> get() = _myFeedList

    private val _feedListByHashTag = SingleLiveEvent<MutableList<Feed>>()
    val feedListByHashTag: LiveData<MutableList<Feed>> get() = _feedListByHashTag

    private val _feedListAll = SingleLiveEvent<MutableList<Feed>>()
    val feedListAll: LiveData<MutableList<Feed>> get() = _feedListAll

    private val _insertFeedInfo = SingleLiveEvent<Feed>()
    val insertFeedInfo: LiveData<Feed> get() = _insertFeedInfo

    fun getMyFeedList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectMyFeeds().let { response ->
                if(response.isSuccessful) {
                    var list = response.body() as MutableList<Feed>
                    // 일단 최신순 정렬?
                    list.sortBy { it.feedCreatedAt }
                    _myFeedList.postValue(list)
                } else {
                    Log.d(TAG, "getMyFeedList: ${response.code()}")
                }
            }
        }
    }

    fun getFeedListByHashTag(fhTag: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectFeedsByHashtag(fhTag).let { response ->
                if(response.isSuccessful) {
                    var list = response.body() as MutableList<Feed>
                    // 일단 최신순 정렬?
                    list.sortBy { it.feedCreatedAt }
                    _feedListByHashTag.postValue(list)
                } else {
                    Log.d(TAG, "getFeedListByHashTag: ${response.code()}")
                }
            }
        }
    }

    fun getFeedListAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectAllFeeds().let { response ->
                if(response.isSuccessful) {
                    var list = response.body() as MutableList<Feed>
                    // 일단 최신순 정렬?
                    list.sortBy { it.feedCreatedAt }
                    _feedListAll.postValue(list)
                } else {
                    Log.d(TAG, "getFeedListAll: ${response.code()}")
                }
            }
        }
    }

    fun insertFeed(feedInfo: Feed) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFeed(feedInfo).let { response ->
                if(response.isSuccessful) {
                    var info = response.body()
                    _insertFeedInfo.postValue(info!!)
                } else {
                    Log.d(TAG, "insertFeed: ${response.code()}")
                }
            }
        }
    }
}