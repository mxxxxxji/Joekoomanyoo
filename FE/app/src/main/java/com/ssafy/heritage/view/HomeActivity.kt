package com.ssafy.heritage.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseActivity
import com.ssafy.heritage.data.dto.User
import com.ssafy.heritage.databinding.ActivityHomeBinding
import com.ssafy.heritage.listener.BackPressedListener
import com.ssafy.heritage.util.Channel.CHANNEL_ID
import com.ssafy.heritage.util.Channel.CHANNEL_NAME
import com.ssafy.heritage.viewmodel.HeritageViewModel
import com.ssafy.heritage.viewmodel.UserViewModel
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


private const val TAG = "HomeActivity___"

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val heritageViewModel by viewModels<HeritageViewModel>()
    private val userViewModel by viewModels<UserViewModel>()
    private lateinit var navController: NavController
    private var backButtonTime = 0L
    lateinit var backPressedListener: BackPressedListener
    var fromHeritageDetailFragment = false

    override fun init() {

        initNavigation()

        initObserver()

        getHashKey()

        getFCMToken()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(CHANNEL_ID, CHANNEL_NAME)
        }
    }

    override fun onStart() {
        super.onStart()
        intent?.getParcelableExtra<User>("user")?.let { userViewModel.setUser(it) }
        heritageViewModel.getHeritageList()
    }

    private fun initObserver() {
        userViewModel.user.observe(this) {
            Log.d(TAG, "initObserver: $it")

            // userViewModel에 있는 user에 데이터가 들어오면 블라블라
            userViewModel.getScrapLIst()

            ApplicationClass.sharedPreferencesUtil.saveUser(it)

        }
    }

    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
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

//        // 바텀네비 중복클릭 방지
//        bottomNavigation.setOnItemReselectedListener { }

        // 뒤로가기 버튼 리스너
        backPressedListener = object : BackPressedListener {
            override fun register() {
                fromHeritageDetailFragment = true
            }

            override fun unregister() {
                fromHeritageDetailFragment = false
            }
        }
    }

    override fun onBackPressed() {
        if (fromHeritageDetailFragment) {
            super.onBackPressed()
        } else {
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
                    finish()
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

    fun getFCMToken() {
        // FCM 토큰 수신
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "FCM 토큰 얻기에 실패하였습니다.", task.exception)
                return@OnCompleteListener
            }

            val token = task.result

            // 서버로 토큰 전송
            userViewModel.pushToken(userViewModel.user.value?.userSeq!!, token)

            // token log 남기기
            Log.d(TAG, "FCMToken: ${token ?: "task.result is null"}")
        })
    }

    // Notification 수신을 위한 채널 추가
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(id: String, name: String) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(id, name, importance)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // EditText가 아닌 다른 곳 터치시 키보드 숨기기
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val view = currentFocus

        if (view != null && view is EditText) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }

        return super.dispatchTouchEvent(ev)
    }
}