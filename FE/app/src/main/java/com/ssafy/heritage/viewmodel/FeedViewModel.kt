package com.ssafy.heritage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.heritage.ApplicationClass.Companion.IMG_URL
import com.ssafy.heritage.data.dto.Feed
import com.ssafy.heritage.data.remote.request.FeedAddRequest
import com.ssafy.heritage.data.remote.response.FeedDetailResponse
import com.ssafy.heritage.data.remote.response.FeedListResponse
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.util.FormDataUtil
import com.ssafy.heritage.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Response
import java.io.File

private const val TAG = "FeedViewModel___"

class FeedViewModel: ViewModel() {

    var job: Job? = null

    private val repository = Repository.get()

    private val _reviewState = SingleLiveEvent<Boolean>()
    val reviewState: LiveData<Boolean> get() = _reviewState

    private val _myFeedList = MutableLiveData<List<FeedListResponse>>()
    val myFeedList: MutableLiveData<List<FeedListResponse>> get() = _myFeedList

    private val _feedListAll = MutableLiveData<List<FeedListResponse>>()
    val feedListAll: MutableLiveData<List<FeedListResponse>> get() = _feedListAll

    private val _feedListByHashTag = MutableLiveData<List<FeedListResponse>>()
    val feedListByHashTag: MutableLiveData<List<FeedListResponse>> get() = _feedListByHashTag

    private val _insertFeedInfo = SingleLiveEvent<String>()
    val insertFeedInfo: LiveData<String> get() = _insertFeedInfo

    private val _feedInfodetail = MutableLiveData<FeedListResponse>()
    val feedInfoDetailL: LiveData<FeedListResponse> get() = _feedInfodetail

    fun add(info: FeedListResponse) {
        _feedInfodetail.postValue(info)
    }

    fun getMyFeedList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectMyFeeds().let { response ->
                if(response.isSuccessful) {
                    var list = response.body() as MutableList<FeedListResponse>
                    _feedListAll.postValue(list)
                    Log.d(TAG, "getMyFeedList: 내 피드 목록 조회 성공")
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
                    _feedListAll.postValue(list)
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
                    Log.d(TAG, "getFeedListAll: 모든 피드 목록 조회 성공")
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
//                    var info = response.body()
//                    _insertFeedInfo.postValue(info)
                    Log.d(TAG, "insertFeed: 피드에 글 들어갔다!")
                } else {
                    Log.d(TAG, "insertFeed: ${response.code()}")
                }
            }
        }
    }

    // 사진 전송하기
    suspend fun sendImage(img: MultipartBody.Part) = withContext(Dispatchers.Main) {

        var response: Response<String>? = null
        job = launch(Dispatchers.Main) {
            response = repository.sendImage("${IMG_URL}/uploadFile", img)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "sendImage response: $it")
            if (it.isSuccessful) {
                val body = JSONObject(it.body())
                val url = body.get("fileDownloadUri")
                Log.d(TAG, "sendImage body: ${url}")
                _insertFeedInfo.value = url as String
                Log.d(TAG, "sendImage: ${url as String}")
                Log.d(TAG, "sendImage: ${_insertFeedInfo.value}")
                true
            } else {
                false
            }
        }
    }

}