package com.ssafy.heritage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.event.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "LoginViewModel___"

class LoginViewModel : ViewModel() {

    private val repository = Repository.get()

    // viewModel에서 Toast 메시지 띄우기 위한 Event
    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    val id = MutableLiveData<String>()

    val pw = MutableLiveData<String>()

    // 일반로그인
    suspend fun login() = withContext(Dispatchers.Main) {

        // id나 pw가 입력되지 않았을 때
        if (id.value.isNullOrBlank() || pw.value.isNullOrBlank()) {
            makeToast("아이디와 비밀번호를 입력해주세요")
            return@withContext false
        }

        // 서버에 로그인 요청
        val map = HashMap<String, String>()

        map.put("userId", id.value!!)
        map.put("userPassword", pw.value!!)

        repository.login(map).let { response ->
            // 로그인 성공했을 경우
            if (response.isSuccessful) {
                // 토큰값 반환
                response.body()
            }
            // 로그인 실패했을 경우
            else {
                Log.d(TAG, "${response.code()}")
                makeToast("아이디, 비밀번호를 확인해주세요")
                null
            }
        }
    }

    // 아이디 중복검사 (소셜용)
    suspend fun socialCheckId(id: String) = withContext(Dispatchers.Main) {
        // 서버에서 아이디 중복여부 요청
        repository.socialCheckId(id).let { response ->
            Log.d(TAG, "socialCheckId: ${response.body()}")
            // 중복이 아닐 경우 - 회원가입 시킴
            if (response.isSuccessful) {
                "signup"
            }
            // 일반로그인 아이디인지, 소셜로그인 아이디인지 판별
            else {
                when (response.body()) {
                    "fail social" -> {
                        socialLogin(id)
                    }
                    "fail none" -> {
                        makeToast("이미 일반로그인으로 가입한 아이디 입니다")
                        "fail"
                    }
                    else -> {
                        makeToast("소셜로그인 오류")
                        "fail"
                    }
                }
            }
        }
    }

    // 소셜로그인 시킴
    suspend fun socialLogin(id: String) = withContext(Dispatchers.Main) {

        val map = HashMap<String, String>()

        map.put("userId", id)
        map.put("userPassword", "0")

        repository.socialLogin(map).let { response ->

            // 로그인 성공한 경우
            if (response.isSuccessful) {
                // 토큰값 반환
                response.body()
            } else {
                Log.d(TAG, "${response.code()}")
                makeToast("소셜 로그인 실패")
                "fail"
            }
        }
    }

    fun makeToast(msg: String) {
        _message.value = Event(msg)
    }
}