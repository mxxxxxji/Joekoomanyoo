package com.ssafy.heritage.view.ar

import android.opengl.GLSurfaceView
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.maps.model.LatLng
import com.google.ar.core.Earth
import com.google.ar.core.GeospatialPose
import com.ssafy.heritage.R
import com.ssafy.heritage.helpers.MapTouchWrapper
import com.ssafy.heritage.helpers.MapView
import com.ssafy.heritage.helpers.SnackbarHelper

class ARGeoView(val activity: ARGeoActivity):DefaultLifecycleObserver {

    val root = View.inflate(activity, R.layout.activity_argeo, null)
    var surfaceView = root.findViewById<GLSurfaceView>(R.id.surfaceview).apply {

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
    val statusText = root.findViewById<TextView>(R.id.statusText)
    fun updateStatusText(earth: Earth, cameraGeospatialPose: GeospatialPose?) {
        activity.runOnUiThread {
            val poseText = if (cameraGeospatialPose == null) "" else
                activity.getString(
                    R.string.geospatial_pose,
                    cameraGeospatialPose.latitude,
                    cameraGeospatialPose.longitude,
                    cameraGeospatialPose.horizontalAccuracy,
                    cameraGeospatialPose.altitude,
                    cameraGeospatialPose.verticalAccuracy,
                    cameraGeospatialPose.heading,
                    cameraGeospatialPose.headingAccuracy
                )
            statusText.text = activity.resources.getString(
                R.string.earth_state,
                earth.earthState.toString(),
                earth.trackingState.toString(),
                poseText
            )
        }
    }
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        var gestureListener = MyGesture()
        var doubleListener = MyDoubleGesture()
        var gesturedetector = GestureDetector(this.activity, gestureListener)
        gesturedetector.setOnDoubleTapListener(doubleListener)
        surfaceView.setOnTouchListener{ v, event->
            return@setOnTouchListener gesturedetector.onTouchEvent(event)
        }
    }
    override fun onResume(owner: LifecycleOwner) {
        surfaceView.onResume()
    }

    override fun onPause(owner: LifecycleOwner) {
        surfaceView.onPause()
    }
}
class MyGesture : GestureDetector.OnGestureListener {

    // 제스처 이벤트를 받아서 text를 변경
    override fun onShowPress(e: MotionEvent?) {
        Log.d("test1","onShowPress")
        Log.d("test1",e.toString())
    }
    override fun onSingleTapUp(e: MotionEvent?): Boolean {

        Log.d("test1","onSingleTapUp")
        Log.d("test1",e.toString())
        return true
    }
    override fun onDown(e: MotionEvent?): Boolean {

        Log.d("test1","onDown")
        Log.d("test1",e.toString())
        return true
    }
    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {

        Log.d("test1","onFling")
        Log.d("test1",e1.toString() + e2.toString())
        return true
    }
    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {

        Log.d("test1","onScroll")
        Log.d("test1",e1.toString() + e2.toString())
        return true
    }
    override fun onLongPress(e: MotionEvent?) {

        Log.d("test1","onLongPress")
        Log.d("test1",e.toString())
    }

}

class MyDoubleGesture : GestureDetector.OnDoubleTapListener
{

    override fun onDoubleTap(e: MotionEvent?): Boolean {

        Log.d("test1","onDoubleTap")
        Log.d("test1",e.toString())
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {

        Log.d("test1","onDoubleTapEvent")
        Log.d("test1",e.toString())
        return true

    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {

        Log.d("test1","onSingleTapConfirmed")
        Log.d("test1",e.toString())
        return true

    }

}