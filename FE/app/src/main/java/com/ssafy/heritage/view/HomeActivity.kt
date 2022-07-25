package com.ssafy.heritage.view

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseActivity
import com.ssafy.heritage.databinding.ActivityHomeBinding

private const val TAG = "MainActivity___"

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private lateinit var navController: NavController

    override fun init() {
        initNavigation()
    }

    // 네비게이션 연결
    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_main) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.bottomNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // 바텀 네비게이션이 표시되는 Fragment
            //if(destination.id == R.id.homeFragment)
            // 바텀 네비게이션이 표시되지 않는 Fragment

        }
    }
}