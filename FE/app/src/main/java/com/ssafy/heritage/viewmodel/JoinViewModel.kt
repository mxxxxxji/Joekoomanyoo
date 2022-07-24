package com.ssafy.heritage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.heritage.event.Event

private const val TAG = "JoinViewModel___"

class JoinViewModel : ViewModel() {

    // viewModel에서 Toast 메시지 띄우기 위한 Event
    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    val id = MutableLiveData<String>()

    val pw = MutableLiveData<String>()
}