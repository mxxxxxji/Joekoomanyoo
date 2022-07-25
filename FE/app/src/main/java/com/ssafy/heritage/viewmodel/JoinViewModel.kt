package com.ssafy.heritage.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.heritage.event.Event
import java.util.regex.Pattern

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

    val birth = MutableLiveData<Int>().apply { value = 20 }

    val gender = MutableLiveData<Char>()

    val login_type = MutableLiveData<String>()

    var isCheckedNickname = MutableLiveData<Boolean>().apply { value = false }

    // id 이메일 인증번호 전송하기 (클릭)
    fun sendIdVeroficationCode(textInputLayout: TextInputLayout): Boolean {

        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        // 이메일 형식이 맞을 경우
        if (!id.value.isNullOrBlank() && pattern.matcher(id.value).matches()) {
            // 서버로 인증 요청 전송

            // 요청 전송 성공시 true 리턴

            // 실패시 false 리턴 (이메일 중복)
            // makeToast("중복된 이메일입니다")
            // makeTextInputLayoutError(textInputLayout,"중복된 이메일입니다")
        } else {
            makeTextInputLayoutError(textInputLayout, "이메일 형식이 올바르지 않습니다")
            makeToast("이메일 형식이 올바르지 않습니다")
            return true // 테스트용
//            return false
        }

        return true
    }

    // id 이메일 인증하기 (클릭)
    fun idVerify(textInputLayout: TextInputLayout): Boolean {

        // 서버로 인증번호 보내고 맞게 입력했는지 검사

        // 인증번호 통과했을 경우
        return true

        // 실패했을 경우
        makeTextInputLayoutError(textInputLayout, "인증번호가 틀렸습니다")
//            return false
    }

    // 닉네임 중복확인 (클릭)
    fun nicknameVerify(textInputLayout: TextInputLayout): Boolean {

        // 서버에 닉네임 중복검사 요청 보냄

        // 중복이 아닐경우
        isCheckedNickname.value = true
        makeToast("사용 가능한 닉네임입니다")
        return true

        // 중복일 경우
        makeTextInputLayoutError(textInputLayout, "중복된 닉네임입니다")
        makeToast("중복된 닉네임입니다")
        return true// 테스트용
//        isCheckedNickname.value = false
//        return false
    }

    // 비밀번호 유효성 검사 실행
    fun validatePw(tilPw: TextInputLayout, tilPwCheck: TextInputLayout): Boolean {
        Log.d(TAG, "validatePw: validatePw")

        // 비밀번호를 입력했는지 검사
        if (pw.value.isNullOrBlank()) {
            Log.d(TAG, "validatePw: ")
            makeTextInputLayoutError(tilPw, "비밀번호를 입력해주세요")
            makeToast("비밀번호를 입력해주세요")
            return false
        }
        // 비밀번호 형식이 틀린 경우
        else {
            val pwRegex = """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).*$"""
            val pwPattern = Pattern.compile(pwRegex)
            if (!pwPattern.matcher(pw.value).matches()) {
                makeTextInputLayoutError(tilPw, "비밀번호를 규칙을 만족하지 못합니다")
                makeToast("비밀번호를 규칙을 만족하지 못합니다")
                return false
            }
        }
        // 비밀번호 재확인 입력했는지 검사
        if (pw_check.value.isNullOrBlank()) {
            makeTextInputLayoutError(tilPwCheck, "비밀번호를 입력해주세요")
            makeToast("비밀번호를 입력해주세요")
            return false
        }
        // 두 비밀번호가 일치하지 않는 경우
        if (!pw.value.equals(pw_check.value)) {
            makeTextInputLayoutError(tilPwCheck, "비밀번호가 일치하지 않습니다")
            makeToast("비밀번호가 일치하지 않습니다")
            return false
        }

        Log.d(TAG, "validatePw: ")
        // 유효성 검사를 다 통과한 경우
        return true
    }

    // 유효성 검사 통과 했는지 여부 (id 유효성 검사 제외)
    fun isValidated() {

    }

    // 성별 선택할 때마다 실행
    fun genderTypeChanged(selected: Char) {
        gender.value = selected
    }

    fun join():Boolean {
        // 서버에 회원가입 요청

        // 회원가입 성공 시
        return true

        // 회원가입 실패 시
    }

    fun makeToast(msg: String) {
        _message.value = Event(msg)
    }

    fun makeTextInputLayoutError(textInputLayout: TextInputLayout, msg: String) {
        textInputLayout.error = msg
        textInputLayout.isErrorEnabled = true
    }
}