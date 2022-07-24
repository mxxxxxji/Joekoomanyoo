package com.ssafy.heritage.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseActivity
import com.ssafy.heritage.databinding.ActivityMainBinding

private const val TAG = "MainActivity___"
class HomeActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_home) {

    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        initNavigation()
    }

    // 네비게이션 연결
    private fun initNavigation(){
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_main) as
//        navController = navHostFragment.findNavController()
//        binding.
//        binding.bottom.setupWithNavController(navController)
//
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            // 바텀 네비게이션이 표시되는 Fragment
//            if(destination.id == R.id.homeFragment || destination.id == R.id.orderFragment
//                || destination.id == R.id.myPageFragment){
//                binding.bottomNavi.visibility = View.VISIBLE
//            }
//            // 바텀 네비게이션이 표시되지 않는 Fragment
//            else{
//                binding.bottomNavi.visibility = View.GONE
//            }
//        }
    }
}