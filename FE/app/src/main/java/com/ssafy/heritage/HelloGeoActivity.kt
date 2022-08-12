
package com.ssafy.heritage

import android.app.Activity
import android.app.ActivityManager
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.core.TrackingState
import com.google.ar.core.exceptions.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.HitTestResult
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.ux.ArFragment
import com.ssafy.heritage.helpers.ARCoreSessionLifecycleHelper
import com.ssafy.heritage.helpers.FullScreenHelper
import com.ssafy.heritage.helpers.GeoPermissionsHelper
import com.ssafy.heritage.samplerender.SampleRender

private const val TAG = "HelloGeoActivity"
class HelloGeoActivity : AppCompatActivity() , Scene.OnUpdateListener, Scene.OnPeekTouchListener {
  lateinit var arCoreSessionHelper: ARCoreSessionLifecycleHelper
  lateinit var view: HelloGeoView
  lateinit var renderer: HelloGeoRenderer
  private var fragment: ArFragment? = null
  private var anchorNode: AnchorNode? = null
  private val MIN_OPENGL_VERSION = 3.0
  private val DRAW_DISTANCE = 0.13f
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
    renderer = HelloGeoRenderer(this)
    lifecycle.addObserver(renderer)

    // Set up Hello AR UI.
    view = HelloGeoView(this)
    lifecycle.addObserver(view)
    setContentView(view.root)

 //   fragment = supportFragmentManager.findFragmentById(R.id.sceneform_fragment) as ArFragment?
//    fragment!!.arSceneView.planeRenderer.isEnabled = false
//    fragment!!.arSceneView.scene.addOnUpdateListener(this)
//    fragment!!.arSceneView.scene.addOnPeekTouchListener(this)

    // Sets up an example renderer using our HelloGeoRenderer.
    SampleRender(view.surfaceView, renderer, assets)
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

  override fun onUpdate(p0: FrameTime?) {
    val camera = fragment!!.arSceneView.arFrame!!.camera
    if (camera.trackingState == TrackingState.TRACKING) {
      fragment!!.planeDiscoveryController.hide()
    }
  }

  override fun onPeekTouch(p0: HitTestResult?, tap: MotionEvent) {
    val action = tap.action
    val camera = fragment!!.arSceneView.scene.camera
    val ray = camera.screenPointToRay(tap.x, tap.y)

    Log.d(TAG, "onPeekTouch: ${ray}")
    Log.d(TAG, "onPeekTouch: ${ray}")
    if (anchorNode == null) {
      val arSceneView = fragment!!.arSceneView
      val coreCamera = arSceneView.arFrame!!.camera
      if (coreCamera.trackingState != TrackingState.TRACKING) {
        return
      }
      val pose = coreCamera.pose
      anchorNode = AnchorNode(arSceneView.session!!.createAnchor(pose))
      anchorNode!!.setParent(arSceneView.scene)
    }
  }
  private fun displayError(throwable: Throwable) {
    Log.e(
      TAG,
      "Unable to create material",
      throwable
    )
    val toast = Toast.makeText(this, "Unable to create material", Toast.LENGTH_LONG)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
  }

  /**
   * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
   * on this device.
   *
   *
   * Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
   *
   *
   * Finishes the activity if Sceneform can not run
   */
  fun checkIsSupportedDeviceOrFinish(activity: Activity): Boolean {
    if (Build.VERSION.SDK_INT < VERSION_CODES.N) {
      Log.e(
        TAG,
        "Sceneform requires Android N or later"
      )
      Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show()
      activity.finish()
      return false
    }
    val openGlVersionString = (activity.getSystemService(ACTIVITY_SERVICE) as ActivityManager)
      .deviceConfigurationInfo
      .glEsVersion
    if (openGlVersionString.toDouble() < MIN_OPENGL_VERSION) {
      Log.e(
        TAG,
        "Sceneform requires OpenGL ES 3.0 later"
      )
      Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
        .show()
      activity.finish()
      return false
    }
    return true
  }
}
