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

    val id_verification_code = MutableLiveData<String>()

    val nickname = MutableLiveData<String>()

    val pw = MutableLiveData<String>()

    val pw_check = MutableLiveData<String>()

    val birth = MutableLiveData<Int>()

    val gender = MutableLiveData<Char>()

    val login_type = MutableLiveData<String>()

    // id 이메일 인증번호 전송 클릭했는지 여부
    var isSendedCode = MutableLiveData<Boolean>().apply { value = false }

    // id 이메일 인증 & 중복검사 통과 여부
    var isIdChecked = MutableLiveData<Boolean>().apply { value = false }


    // id 이메일 인증번호 전송하기 (클릭)
    fun SendIdVeroficationCode() {

        // 이메일 형식 유효성 통과

        // 서버로 인증 요청 전송

        // 요청 인증이 성공했을 경우
        isSendedCode.value = true
    }

    // id 이메일 인증하기 (클릭)
    fun idVerify() {

        // 서버로 인증번호 보내고 맞게 입력했는지 검사

        // 인증번호 통과했을 경우
        isIdChecked.value = true
    }

    // 성별 선택할 때마다 실행
    fun genderTypeChanged(selected: Char) {
        gender.value = selected
    }

    // 첫번째 패스워드와 두번쨰 패스워드가 일치하는지 여부
    fun isSamePw() {

    }

    // 유효성 검사 통과 했는지 여부 (id 유효성 검사 제외)
    fun isValidated() {

    }

    // id 유효성 검사 통과했는지 여부
    fun isIdValidated() {

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