package com.ssafy.heritage.view.ar

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.core.exceptions.*
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentARPlayBinding
import com.ssafy.heritage.helpers.ARCoreSessionLifecycleHelper
import com.ssafy.heritage.helpers.FullScreenHelper
import com.ssafy.heritage.helpers.GeoPermissionsHelper
import com.ssafy.heritage.samplerender.SampleRender

private const val TAG = "ARPlayFragment___"

class ARPlayFragment : BaseFragment<FragmentARPlayBinding>(R.layout.fragment_a_r_play) {

    lateinit var arCoreSessionHelper: ARCoreSessionLifecycleHelper
    lateinit var view: GeoView
    lateinit var renderer: GeoRenderer
    override fun init() {
        onWindowFocusChanged(requireActivity().hasWindowFocus())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 세션 생명주기 헬퍼, 구성 설정
        arCoreSessionHelper = ARCoreSessionLifecycleHelper(requireActivity())
        // 세션의 상태 정보
        arCoreSessionHelper.exceptionCallback =
            { exception ->
                val message =
                    when (exception) {
                        is UnavailableUserDeclinedInstallationException ->
                            "Please install Google Play Services for AR"
                        is UnavailableApkTooOldException -> "Please update ARCore"
                        is UnavailableSdkTooOldException -> "Please update this app"
                        is UnavailableDeviceNotCompatibleException -> "This device does not support AR"
                        is CameraNotAvailableException -> "Camera not available. Try restarting the app."
                        else -> "Failed to create AR session: $exception"
                    }
                Log.e(TAG, "ARCore threw an exception", exception)
                //view.snackbarHelper.showError(this, message)
            }
        // 세션 기능 구성
        arCoreSessionHelper.beforeSessionResume = ::configureSession
        lifecycle.addObserver(arCoreSessionHelper)

        // Set up the Hello AR renderer.
        renderer = GeoRenderer(this)

        // Set up Hello AR UI.
        lifecycle.addObserver(renderer)
        view = GeoView(fragment = ARPlayFragment())
        lifecycle.addObserver(view)

        // Sets up an example renderer using our HelloGeoRenderer.
        SampleRender(view.surfaceView, renderer, requireActivity().assets)

    }
    // 세션을 구성하고, 사용 사례에 따라 원하는 옵션 설정
    fun configureSession(session: Session) {
        // 세션 구성의 GeospatialMode를 ENABLED로 변경
        session.configure(
            session.config.apply {
                // 애플리케이션에서 지리정보를 가져올 수 있음
                geospatialMode = Config.GeospatialMode.ENABLED
            }
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        results: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, results)
        if (!GeoPermissionsHelper.hasGeoPermissions(requireActivity())) {
            // Use toast instead of snackbar here since the activity will exit.
            Toast.makeText(requireActivity(), "Camera and location permissions are needed to run this application", Toast.LENGTH_LONG)
                .show()
            if (!GeoPermissionsHelper.shouldShowRequestPermissionRationale(requireActivity())) {
                // Permission denied with checking "Do not ask again".
                GeoPermissionsHelper.launchPermissionSettings(requireActivity())
            }
            requireActivity().finish()
        }
    }
    fun onWindowFocusChanged(hasFocus: Boolean) {
        requireActivity().onWindowFocusChanged(hasFocus)
        FullScreenHelper.setFullScreenOnWindowFocusChanged(requireActivity(), hasFocus)
    }



}