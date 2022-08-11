
package com.ssafy.heritage

import com.ssafy.heritage.helpers.MapTouchWrapper
import com.ssafy.heritage.helpers.SnackbarHelper
import android.opengl.GLSurfaceView
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.ar.core.Earth
import com.google.ar.core.GeospatialPose
import com.google.ar.core.dependencies.e
import com.google.ar.sceneform.ux.ArFragment
import com.ssafy.heritage.helpers.MapView
import com.ssafy.heritage.helpers.TapHelper

private const val TAG = "HelloGeoView___"
class HelloGeoView(val activity: HelloGeoActivity) : DefaultLifecycleObserver{

    val root = View.inflate(activity, R.layout.activity_hello_geo, null)

  val surfaceView = root.findViewById<GLSurfaceView>(R.id.surfaceview).apply {
      this.setOnTouchListener { view, motionEvent ->
          Log.d(TAG, "${motionEvent.action}:${ motionEvent.x}, ${ motionEvent.y} ")
          true
      }
  }

  val session
    get() = activity.arCoreSessionHelper.session

  val snackbarHelper = SnackbarHelper()

  var mapView: MapView? = null
  val mapTouchWrapper = root.findViewById<MapTouchWrapper>(R.id.map_wrapper).apply {
    setup { screenLocation ->
      val latLng: LatLng =
        mapView?.googleMap?.projection?.fromScreenLocation(screenLocation) ?: return@setup
      activity.renderer.onMapClick(latLng)
    }
  }

  val mapFragment =
    (activity.supportFragmentManager.findFragmentById(R.id.map)!! as SupportMapFragment).also {
      it.getMapAsync { googleMap -> mapView = MapView(activity, googleMap) }
    }
    val fragmentWrapper = root.findViewById<MapTouchWrapper>(R.id.fragment_wrapper).apply {
        setup { screenLocation->
            Log.d(TAG, screenLocation.toString())
            if(activity.renderer.earthAnchor?.pose == screenLocation) {
                Log.d(TAG, activity.renderer.earthAnchor?.pose.toString())
            }
            Log.d(TAG, "dddddd${activity.renderer.earthAnchor?.pose.toString()}")
        }
    }
  val statusText = root.findViewById<TextView>(R.id.statusText)
  fun updateStatusText(earth: Earth, cameraGeospatialPose: GeospatialPose?) {
    activity.runOnUiThread {
      val poseText = if (cameraGeospatialPose == null) "" else
        activity.getString(R.string.geospatial_pose,
                           cameraGeospatialPose.latitude,
                           cameraGeospatialPose.longitude,
                           cameraGeospatialPose.horizontalAccuracy,
                           cameraGeospatialPose.altitude,
                           cameraGeospatialPose.verticalAccuracy,
                           cameraGeospatialPose.heading,
                           cameraGeospatialPose.headingAccuracy)
      statusText.text = activity.resources.getString(R.string.earth_state,
                                                     earth.earthState.toString(),
                                                     earth.trackingState.toString(),
                                                     poseText)
    }
  }

  override fun onResume(owner: LifecycleOwner) {
    surfaceView.onResume()
  }

  override fun onPause(owner: LifecycleOwner) {
    surfaceView.onPause()
  }
}
