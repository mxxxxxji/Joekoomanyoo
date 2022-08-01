package com.ssafy.heritage.view

import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseActivity
import com.ssafy.heritage.databinding.ActivityHomeBinding
import com.ssafy.heritage.viewmodel.HeritageViewModel

private const val TAG = "HomeActivity___"

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val heritageViewModel by viewModels<HeritageViewModel>()
    private lateinit var navController: NavController
    private var backButtonTime = 0L

    override fun init() {
        initNavigation()
    }

    override fun onStart() {
        super.onStart()
        heritageViewModel.getHeritageList()
    }

    // 네비게이션 연결
    private fun initNavigation() = with(binding) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_main) as NavHostFragment
        navController = navHostFragment.findNavController()
        bottomNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // 바텀 네비게이션이 표시되는 Fragment
            //if(destination.id == R.id.homeFragment)
            // 바텀 네비게이션이 표시되지 않는 Fragment

        }
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        val gapTime = currentTime - backButtonTime
//        val currentFragment = supportFragmentManager.findFragmentById(galleryFragment.id)
        //첫 화면(바텀 네비 화면들)이면 뒤로가기 시 앱 종료
        if (navController.currentDestination!!.id == R.id.groupListFragment ||
            navController.currentDestination!!.id == R.id.heritageListFragment ||
            navController.currentDestination!!.id == R.id.homeFragment ||
            navController.currentDestination!!.id == R.id.feedListFragment ||
            navController.currentDestination!!.id == R.id.ARFragment
        ) {
            if (gapTime in 0..2000) {
                //2초 안에 두 번 뒤로가기 누를 시 앱 종료
                finishAndRemoveTask()
            } else {
                backButtonTime = currentTime
                Toast.makeText(this, "뒤로가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            }
        }
        //첫 화면(바텀 네비 화면들)이 아니면
        else {
            //Navigation의 스택에서 pop 됨(원래 동작)
            super.onBackPressed()
        }
    }
}