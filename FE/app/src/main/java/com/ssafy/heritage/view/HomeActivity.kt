package com.ssafy.heritage.view

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.util.Base64
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.RequestWorker
import com.ssafy.heritage.base.BaseActivity
import com.ssafy.heritage.data.dto.User
import com.ssafy.heritage.databinding.ActivityHomeBinding
import com.ssafy.heritage.listener.BackPressedListener
import com.ssafy.heritage.util.Channel.CHANNEL_ID
import com.ssafy.heritage.util.Channel.CHANNEL_NAME
import com.ssafy.heritage.viewmodel.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit


private const val TAG = "HomeActivity___"
private val PERMISSIONS_REQUIRED = Manifest.permission.ACCESS_FINE_LOCATION


class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val heritageViewModel by viewModels<HeritageViewModel>()
    private val userViewModel by viewModels<UserViewModel>()
    private val groupViewModel by viewModels<GroupViewModel>()
    private val feedViewModel by viewModels<FeedViewModel>()

    private lateinit var navController: NavController
    private var backButtonTime = 0L
    lateinit var backPressedListener: BackPressedListener
    var fromHeritageDetailFragment = false

    private var lat = 0.0    // 위도
    private var lng = 0.0    // 경도
    private val locationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }


    override fun init() {

        CoroutineScope(Dispatchers.Main).launch {
            intent?.getParcelableExtra<User>("user")?.let { userViewModel.setUser(it) }

            userViewModel.getMyStamp()

            requestPermissionLancher.launch(PERMISSIONS_REQUIRED)

            initNavigation()

            initObserver()

            initClickListener()

            getHashKey()

            getFCMToken()

            periodicWorkRequest()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(CHANNEL_ID, CHANNEL_NAME)
            }

        }
    }


    private fun initObserver() {
        userViewModel.user.observe(this) {
            Log.d(TAG, "initObserver: $it")

            // userViewModel에 있는 user에 데이터가 들어오면 블라블라
            userViewModel.getScrapLIst()

            groupViewModel.selectMyGroups()

            feedViewModel.getFeedListAll()

            ApplicationClass.sharedPreferencesUtil.saveUser(it)

        }
    }

    private fun initClickListener() = with(binding) {
        fab.setOnClickListener {
            bottomNavigation.selectedItemId = R.id.ARFragment
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

        FirebaseApp.initializeApp(this)

        // FCM 토큰 수신
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "FCM 토큰 얻기에 실패하였습니다.", task.exception)
                return@OnCompleteListener
            }

            val token = task.result

            // token log 남기기
            Log.d(TAG, "FCMToken: ${token ?: "task.result is null"}")

            // 서버로 토큰 전송
            userViewModel.pushToken(userViewModel.user.value?.userSeq!!, token)
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

    // 위치 권한 체크 해주고 이후 동작 설정
    private val requestPermissionLancher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // PERMISSION GRANTED
                getLastLocation()

                CoroutineScope(Dispatchers.Main).launch {
                    heritageViewModel.getOrderHeritage()
                }

            } else {
                // PERMISSION NOT GRANTED
                makeToast("위치 권한이 필요합니다")
            }
        }

    private fun getLastLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLancher.launch(PERMISSIONS_REQUIRED)
            return
        }
        locationManager
            .getLastKnownLocation(LocationManager.GPS_PROVIDER)
            .apply {
                if (this != null) {
                    lat = latitude
                    lng = longitude
                }
            }

        locationManager
            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            .apply {
                if (this != null) {
                    lat = latitude
                    lng = longitude
                }
            }

        locationManager
            .getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
            .apply {
                if (this != null) {
                    lat = latitude
                    lng = longitude
                }
            }

        heritageViewModel.setLocation(lat.toString(), lng.toString())
    }

    private fun periodicWorkRequest() {
        //파라미터와 지연 시간 설정
        val workRequest = PeriodicWorkRequestBuilder<RequestWorker>(15, TimeUnit.MINUTES).build()
        //워크매니저 생성
        val workManager = WorkManager.getInstance(applicationContext)
        //워크매니저 실행
        workManager.enqueue(workRequest)
    }

    private fun makeToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
