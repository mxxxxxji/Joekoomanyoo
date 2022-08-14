
package com.ssafy.heritage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.core.exceptions.*
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.ssafy.heritage.helpers.ARCoreSessionLifecycleHelper
import com.ssafy.heritage.helpers.FullScreenHelper
import com.ssafy.heritage.helpers.GeoPermissionsHelper
import com.ssafy.heritage.samplerender.SampleRender
import com.ssafy.heritage.view.HomeActivity
import com.ssafy.heritage.view.ar.ARListFragment
import com.ssafy.heritage.view.dialog.ARCheckDialog
import com.ssafy.heritage.view.dialog.ARCheckDialogInterface
import com.ssafy.heritage.viewmodel.ARViewModel
import com.ssafy.heritage.viewmodel.UserViewModel

private const val TAG = "HelloGeoActivity"
class HelloGeoActivity : AppCompatActivity() , ARCheckDialogInterface{

  lateinit var arCoreSessionHelper: ARCoreSessionLifecycleHelper
  lateinit var view: HelloGeoView
  lateinit var renderer: HelloGeoRenderer
  private val arViewModel by viewModels<ARViewModel>()
  val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()

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
//        view.btnCheck.setOnClickListener{
//          val dialog = ARCheckDialog(this, this)
//          dialog.show()
//        }
      }

    // Configure session features.
    arCoreSessionHelper.beforeSessionResume = ::configureSession
    lifecycle.addObserver(arCoreSessionHelper)

    // Set up the Hello AR renderer.
    renderer = HelloGeoRenderer(this)
    lifecycle.addObserver(renderer)

    // Set up Hello AR UI.
    view = HelloGeoView(this)
    lifecycle.addObserver(view)
    setContentView(view.root)

    // Sets up an example renderer using our HelloGeoRenderer.
    SampleRender(view.surfaceView, renderer, assets)
    view.btnCheck.setOnClickListener{
      //arViewModel.addStamp(userSeq)
      val dialog = ARCheckDialog(it.context, this)
      dialog.show()
    }
  }

  // Configure the session, setting the desired options according to your usecase.
  fun configureSession(session: Session) {
    session.configure(
      session.config.apply {
        // Enable Geospatial Mode.
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

  override fun onHomeBtnClicked() {
    val intent = Intent(this, HomeActivity::class.java)
    startActivity(intent)
  }

  override fun onARListBtnClicked() {
    finish()
  }
}
