package com.ssafy.heritage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.heritage.event.Event

private const val TAG = "LoginViewModel___"

class LoginViewModel : ViewModel() {

    // viewModel에서 Toast 메시지 띄우기 위한 Event
    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    val id = MutableLiveData<String>()

    val pw = MutableLiveData<String>()

    fun login() {

        // id나 pw가 입력되지 않았을 때
        if (id.value.isNullOrBlank() || pw.value.isNullOrBlank()) {
            _message.value = Event("아이디와 패스워드를 입력해주세요")
        }
    }
}