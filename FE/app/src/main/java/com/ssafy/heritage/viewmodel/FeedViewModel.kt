package com.ssafy.heritage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.heritage.data.dto.Feed
import com.ssafy.heritage.data.remote.request.FeedAddRequest
import com.ssafy.heritage.data.remote.response.FeedListResponse
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "FeedViewModel___"

class FeedViewModel: ViewModel() {

    private val repository = Repository.get()

    private val _myFeedList = SingleLiveEvent<MutableList<FeedListResponse>>()
    val myFeedList: LiveData<MutableList<FeedListResponse>> get() = _myFeedList

    private val _feedListByHashTag = SingleLiveEvent<MutableList<FeedListResponse>>()
    val feedListByHashTag: LiveData<MutableList<FeedListResponse>> get() = _feedListByHashTag

    private val _feedListAll = SingleLiveEvent<MutableList<FeedListResponse>>()
    val feedListAll: LiveData<MutableList<FeedListResponse>> get() = _feedListAll

    private val _insertFeedInfo = SingleLiveEvent<FeedListResponse>()
    val insertFeedInfo: LiveData<FeedListResponse> get() = _insertFeedInfo

    fun getMyFeedList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectMyFeeds().let { response ->
                if(response.isSuccessful) {
                    var list = response.body() as MutableList<FeedListResponse>
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
                    var list = response.body() as MutableList<FeedListResponse>
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
                    var list = response.body() as MutableList<FeedListResponse>
                    _feedListAll.postValue(list)
                } else {
                    Log.d(TAG, "getFeedListAll: ${response.code()}")
                }
            }
        }
    }

    fun insertFeed(feedInfo: FeedAddRequest) {
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