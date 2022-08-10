package com.ssafy.heritage.view.ar

import android.opengl.Matrix
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.maps.model.LatLng
import com.google.ar.core.Anchor
import com.google.ar.core.TrackingState
import com.google.ar.core.exceptions.CameraNotAvailableException
import com.ssafy.heritage.helpers.DisplayRotationHelper
import com.ssafy.heritage.helpers.TrackingStateHelper
import com.ssafy.heritage.samplerender.*
import com.ssafy.heritage.samplerender.arcore.BackgroundRenderer
import java.io.IOException

private const val TAG = "HelloGeoRenderer___"
class GeoRenderer(val fragment: ARPlayFragment) :
    SampleRender.Renderer, DefaultLifecycleObserver {
    companion object {
        private val Z_NEAR = 0.1f
        private val Z_FAR = 1000f
    }

    lateinit var backgroundRenderer: BackgroundRenderer
    lateinit var virtualSceneFramebuffer: Framebuffer
    var hasSetTextureNames = false

    // 가상 객체
    lateinit var virtualObjectMesh: Mesh
    lateinit var virtualObjectShader: Shader
    lateinit var virtualObjectTexture: Texture

    // 각 프레임에 대한 할당 수를 줄이기 위해 여기에 할당된 임시 행렬
    val modelMatrix = FloatArray(16)
    val viewMatrix = FloatArray(16)
    val projectionMatrix = FloatArray(16)
    val modelViewMatrix = FloatArray(16) // view x model

    val modelViewProjectionMatrix = FloatArray(16) // projection x view x model

    val session
        get() = fragment.arCoreSessionHelper.session

    val displayRotationHelper = DisplayRotationHelper(fragment.requireActivity())
    val trackingStateHelper = TrackingStateHelper(fragment.requireActivity())

    override fun onResume(owner: LifecycleOwner) {
        displayRotationHelper.onResume()
        hasSetTextureNames = false
    }

    override fun onPause(owner: LifecycleOwner) {
        displayRotationHelper.onPause()
    }
    override fun onSurfaceCreated(render: SampleRender) {
        // Prepare the rendering objects.
        // This involves reading shaders and 3D model files, so may throw an IOException.
        try {
            backgroundRenderer = BackgroundRenderer(render)
            virtualSceneFramebuffer = Framebuffer(render, /*width=*/ 1, /*height=*/ 1)

            // Virtual object to render (Geospatial Marker)
            virtualObjectTexture =
                Texture.createFromAsset(
                    render,
                    "models/plant.jpg",
                    Texture.WrapMode.CLAMP_TO_EDGE,
                    Texture.ColorFormat.SRGB
                )

            virtualObjectMesh = Mesh.createFromAsset(render, "models/plant.obj");
            virtualObjectShader =
                Shader.createFromAssets(
                    render,
                    "shaders/ar_unlit_object.vert",
                    "shaders/ar_unlit_object.frag",
                    /*defines=*/ null)
                    .setTexture("u_Texture", virtualObjectTexture)

            backgroundRenderer.setUseDepthVisualization(render, false)
            backgroundRenderer.setUseOcclusion(render, false)
        } catch (e: IOException) {
            Log.e(TAG, "Failed to read a required asset file", e)
            showError("Failed to read a required asset file: $e")
        }
    }

    override fun onSurfaceChanged(render: SampleRender?, width: Int, height: Int) {
        displayRotationHelper.onSurfaceChanged(width, height)
        virtualSceneFramebuffer.resize(width, height)

    }

    override fun onDrawFrame(render: SampleRender) {
        val session = session ?: return

        //<editor-fold desc="ARCore frame boilerplate" defaultstate="collapsed">
        // Texture names should only be set once on a GL thread unless they change. This is done during
        // onDrawFrame rather than onSurfaceCreated since the session is not guaranteed to have been
        // initialized during the execution of onSurfaceCreated.
        if (!hasSetTextureNames) {
            session.setCameraTextureNames(intArrayOf(backgroundRenderer.cameraColorTexture.textureId))
            hasSetTextureNames = true
        }

        // -- Update per-frame state

        // Notify ARCore session that the view size changed so that the perspective matrix and
        // the video background can be properly adjusted.
        displayRotationHelper.updateSessionIfNeeded(session)

        // Obtain the current frame from ARSession. When the configuration is set to
        // UpdateMode.BLOCKING (it is by default), this will throttle the rendering to the
        // camera framerate.
        val frame =
            try {
                session.update()
            } catch (e: CameraNotAvailableException) {
                Log.e(TAG, "Camera not available during onDrawFrame", e)
                showError("Camera not available. Try restarting the app.")
                return
            }

        val camera = frame.camera

        // BackgroundRenderer.updateDisplayGeometry must be called every frame to update the coordinates
        // used to draw the background camera image.
        backgroundRenderer.updateDisplayGeometry(frame)

        // Keep the screen unlocked while tracking, but allow it to lock when tracking stops.
        trackingStateHelper.updateKeepScreenOnFlag(camera.trackingState)

        // -- Draw background
        if (frame.timestamp != 0L) {
            // Suppress rendering if the camera did not produce the first frame yet. This is to avoid
            // drawing possible leftover data from previous sessions if the texture is reused.
            backgroundRenderer.drawBackground(render)
        }

        // If not tracking, don't draw 3D objects.
        if (camera.trackingState == TrackingState.PAUSED) {
            return
        }

        // Get projection matrix.
        camera.getProjectionMatrix(projectionMatrix, 0, Z_NEAR, Z_FAR)

        // Get camera matrix and draw.
        camera.getViewMatrix(viewMatrix, 0)

        render.clear(virtualSceneFramebuffer, 0f, 0f, 0f, 0f)
        //</editor-fold>




        // 지리 정보를 얻어 지도에 표시
        val earth = session.earth
        // earth 객체 사용 가능
        if (earth?.trackingState == TrackingState.TRACKING) {
            // ARCore의 지리정보 요청
            // 위도와 경도로 표현된 위치(GeospatialPose)를 변수에 저장
            val cameraGeospatialPose = earth.cameraGeospatialPose
            // 지도의 위치를 지속적으로 업데이트
            fragment.view.mapView?.updateMapPosition(
                latitude = cameraGeospatialPose.latitude,
                longitude = cameraGeospatialPose.longitude,
                heading = cameraGeospatialPose.heading
            )
            Log.d(TAG, "${cameraGeospatialPose.latitude}%%%%%${cameraGeospatialPose.longitude}")
        }

        // Draw the placed anchor, if it exists.
        earthAnchor?.let {
            render.renderCompassAtAnchor(it)
        }

        // 베경으로 가상장면 구성
        backgroundRenderer.drawVirtualScene(render, virtualSceneFramebuffer, Z_NEAR, Z_FAR)

    }

    var earthAnchor: Anchor? = null

    fun onMapClick(latLng: LatLng) {
        // 어스 객체의 TrackingState가 TRACKING인지 확인 = 어스의 위치를 알고 있어야함
        val latTest = LatLng(36.1000378,128.427956)
        val earth = session?.earth ?: return
        if (earth.trackingState != TrackingState.TRACKING) {
            return
        }
        earthAnchor?.detach() //earthAnchor를 새 앵커로 변경

        // cameraGeopospatialPose를 사용하여 새 앵커의 고도 결정
        // (임시) 좌표를 탭하면 지도상의 위치를 앵커 위치로 배치하여 사용
        // (임시) 어스 앵커를 카메라와 같은 높이에 놓는다
        val altitude = earth.cameraGeospatialPose.altitude - 1
        // 좌표계에서 앵커의 회전 쿼터니언...? 무슨소린지..
        val qx = 1f
        val qy = 1f
        val qz = 1f
        val qw = 1f
        // createAnchor는 좌표에 고정된 Anchor을 만든다
        earthAnchor =
            earth.createAnchor(latTest.latitude, latTest.longitude, altitude, qx, qy, qz, qw)

        // 지도에 마커 표시
        fragment.view.mapView?.earthMarker?.apply {
            position = latTest
            isVisible = true
        }
    }
    private fun SampleRender.renderCompassAtAnchor(anchor: Anchor) {
        // Get the current pose of the Anchor in world space. The Anchor pose is updated
        // during calls to session.update() as ARCore refines its estimate of the world.
        anchor.pose.toMatrix(modelMatrix, 0)

        // Calculate model/view/projection matrices
        Matrix.multiplyMM(modelViewMatrix, 0, viewMatrix, 0, modelMatrix, 0)
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0, modelViewMatrix, 0)

        // Update shader properties and draw
        virtualObjectShader.setMat4("u_ModelViewProjection", modelViewProjectionMatrix)
        draw(virtualObjectMesh, virtualObjectShader, virtualSceneFramebuffer)
    }
    private fun showError(errorMessage: String) =
        fragment.view.snackbarHelper.showError(fragment.activity, errorMessage)



}