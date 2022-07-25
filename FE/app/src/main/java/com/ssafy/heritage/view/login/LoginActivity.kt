package com.ssafy.heritage.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.heritage.R
import com.ssafy.heritage.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        initView()
    }

    private fun initView() {

        // 시작시 LoginFragment 띄움
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_login, LoginFragment())
            .commit()
    }
}