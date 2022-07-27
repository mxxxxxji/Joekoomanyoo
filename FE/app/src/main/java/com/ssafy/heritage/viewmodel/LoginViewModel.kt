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

    // 일반로그인
    fun login(): Boolean {

        // id나 pw가 입력되지 않았을 때
        if (id.value.isNullOrBlank() || pw.value.isNullOrBlank()) {
            makeToast("아이디와 비밀번호를 입력해주세요")
            return false
        }

        // 서버에 로그인 요청

        //// 로그인 성공했을 경우
        return true

        //// 로그인 실패했을 경우
        makeToast("아이디나 비밀번호를 확인해주세요")
        return false
    }

    // 아이디 중복검사 (소셜용)
    fun checkId(id: String): Boolean {
        // 서버에서 아이디 중복여부 요청
        return true// 테스트용

        //// 중복일 경우 - 로그인 시킴
        //// 토근도 반환받음

        return false
        //// 중복이 아닐 경우 - 회원가입 시킴
        return true
    }


    fun makeToast(msg: String) {
        _message.value = Event(msg)
    }
}