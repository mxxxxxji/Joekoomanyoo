package com.ssafy.heritage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.heritage.data.dto.Stamp
import com.ssafy.heritage.data.dto.StampCategory
import com.ssafy.heritage.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

private const val TAG = "ARViewModel___"

class ARViewModel : ViewModel() {

    var job: Job? = null

    private val repository = Repository.get()

    private val _stampList = MutableLiveData<MutableList<Stamp>>()
    val stampList: LiveData<MutableList<Stamp>>
        get() = _stampList

    private val _categoryList = MutableLiveData<List<StampCategory>>()
    val categoryList: LiveData<List<StampCategory>>
        get() = _categoryList


    // 전체 스탬프 목록 불러오기
    fun getAllStamp() = viewModelScope.launch {
        var response: Response<List<Stamp>>? = null
        job = launch(Dispatchers.Main) {
            response = repository.selectAllStamp()
        }
        job?.join()

        response?.let {
            Log.d(TAG, "getMyStamp response: $it")
            if (it.isSuccessful) {
                Log.d(TAG, "getMyStamp body: ${it.body()}")
                _stampList.value = it.body() as MutableList<Stamp>?
            } else {
                Log.d(TAG, "getMyStamp: ${it.errorBody()}")
            }
        }
    }

    // 스탬프 카테고리별 개수 불러오기
    fun getStampCategory() = viewModelScope.launch {
        var response: Response<List<StampCategory>>? = null
        job = launch(Dispatchers.Main) {
            response = repository.selectStampCategory()
        }
        job?.join()

        response?.let {
            Log.d(TAG, "getStampCategory response: $it")
            if (it.isSuccessful) {
                Log.d(TAG, "getStampCategory Success: ${it.body()}")
                val list = it.body()
                val newList = list?.filter { it.categoryCnt > 0 }
                _categoryList.postValue(newList!!)
            } else {
                Log.d(TAG, "getStampCategory error: ${it.errorBody()}")

            }
        }
    }
}