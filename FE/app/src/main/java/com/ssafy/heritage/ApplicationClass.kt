package com.ssafy.heritage

import android.app.Application
import com.ssafy.heritage.data.repository.Repository

class ApplicationClass : Application() {
    companion object{

        // 주소
        const val BASE_URL=""
        //const val GROUP_URL=""
    }
    override fun onCreate() {
        super.onCreate()

        initRepository()
    }

    private fun initRepository(){
        Repository.initialize(this)
    }
}