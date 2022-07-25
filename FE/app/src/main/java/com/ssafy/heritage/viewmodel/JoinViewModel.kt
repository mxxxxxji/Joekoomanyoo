package com.ssafy.heritage.viewmodel

import android.util.Log
import android.widget.RadioGroup
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

    val id_verification_code = MutableLiveData<String>()

    val nickname = MutableLiveData<String>()

    val pw = MutableLiveData<String>()

    val pw_check = MutableLiveData<String>()

    val birth = MutableLiveData<Int>()

    val gender = MutableLiveData<Char>()

    val login_type = MutableLiveData<String>()

    fun genderTypeChanged(selected: Char) {
        gender.value = selected
    }

    fun join() {
        //회원가입 로직 짜야함

//        Log.d(TAG, "id: ${id.value}")
//        Log.d(TAG, "id_verification_code: ${id_verification_code.value}")
//        Log.d(TAG, "nickname: ${nickname.value}")
//        Log.d(TAG, "pw: ${pw.value}")
//        Log.d(TAG, "pw_check: ${pw_check.value}")
//        Log.d(TAG, "birth: ${birth.value}")
//        Log.d(TAG, "gender: ${gender.value}")
    }

}