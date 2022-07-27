package com.ssafy.heritage

import android.app.Application
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.util.SharedPreferencesUtil

class ApplicationClass : Application() {
    companion object{
        lateinit var sharedPreferencesUtil: SharedPreferencesUtil
        // 주소
        const val BASE_URL=""
    }
    override fun onCreate() {
        super.onCreate()

        initRepository()

        // shared preference 초기화
        sharedPreferencesUtil = SharedPreferencesUtil(this)
    }
    private fun initRepository(){
        Repository.initialize(this)
    }
}