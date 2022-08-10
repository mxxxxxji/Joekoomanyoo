package com.ssafy.heritage.view.ar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.core.exceptions.*
import com.ssafy.heritage.R
import com.ssafy.heritage.helpers.ARCoreSessionLifecycleHelper
import com.ssafy.heritage.helpers.FullScreenHelper
import com.ssafy.heritage.helpers.GeoPermissionsHelper
import com.ssafy.heritage.samplerender.SampleRender

private const val TAG = "ARGeoActivity"

class ARGeoActivity : AppCompatActivity() {

    lateinit var arCoreSessionHelper: ARCoreSessionLifecycleHelper
    lateinit var view: ARGeoView
    lateinit var renderer: ARGeoRenderer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Setup ARCore session lifecycle helper and configuration.
        arCoreSessionHelper = ARCoreSessionLifecycleHelper(this)
        // If Session creation or Session.resume() fails, display a message and log detailed
        // information.
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
                view.snackbarHelper.showError(this, message)
            }
        // Configure session features.
        arCoreSessionHelper.beforeSessionResume = ::configureSession
        lifecycle.addObserver(arCoreSessionHelper)

        // Set up the Hello AR renderer.
        renderer = ARGeoRenderer(this)
        lifecycle.addObserver(renderer)

        // Set up Hello AR UI.
        view = ARGeoView(this)
        lifecycle.addObserver(view)
        setContentView(view.root)

        // Sets up an example renderer using our HelloGeoRenderer.
        SampleRender(view.surfaceView, renderer, assets)
    }
    // Configure the session, setting the desired options according to your usecase.
    // 세션을 구성하고, 사용 사례에 따라 원하는 옵션 설정
    fun configureSession(session: Session) {
        // 세션 구성의 GeospatialMode를 ENABLED로 변경
        session.configure(
            session.config.apply {
                // Enable Geospatial Mode.
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
        if (!GeoPermissionsHelper.hasGeoPermissions(this)) {
            // Use toast instead of snackbar here since the activity will exit.
            Toast.makeText(this, "Camera and location permissions are needed to run this application", Toast.LENGTH_LONG)
                .show()
            if (!GeoPermissionsHelper.shouldShowRequestPermissionRationale(this)) {
                // Permission denied with checking "Do not ask again".
                GeoPermissionsHelper.launchPermissionSettings(this)
            }
            finish()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        FullScreenHelper.setFullScreenOnWindowFocusChanged(this, hasFocus)
    }
}