package com.ssafy.heritage.view.login

import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseActivity
import com.ssafy.heritage.databinding.ActivityLoginBinding

private const val TAG = "LoginActivity___"

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override fun init() {

        // 시작시 LoginFragment 띄움
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_login, LoginFragment())
            .commit()
    }
}