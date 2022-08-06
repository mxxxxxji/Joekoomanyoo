package com.ssafy.heritage.view.profile

import androidx.fragment.app.activityViewModels
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentMyDataBinding
import com.ssafy.heritage.viewmodel.UserViewModel
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

private const val TAG = "MyDataFragment___"

class MyDataFragment : BaseFragment<FragmentMyDataBinding>(R.layout.fragment_my_data),
    MapView.POIItemEventListener {

    private val userViewModel by activityViewModels<UserViewModel>()

    private lateinit var mapView: MapView

    override fun init() {

        initMap()

        initObserver()
    }

    override fun onStart() {
        super.onStart()
        userViewModel.getMydestination()
    }

    private fun initMap() = with(binding) {
        mapView = MapView(requireContext())

        // 마커 클릭 리스너 설정
        mapView.setPOIItemEventListener(
            this@MyDataFragment
        )

        map.addView(mapView)

        mapView.setZoomLevel(12, true)
    }

    private fun initObserver() {
        userViewModel.myDestinationList.observe(viewLifecycleOwner) {
            makeMarker()
        }
    }

    private fun makeMarker() {

        userViewModel.myDestinationList.value?.forEachIndexed { index, destination ->
            MapPOIItem().apply {
                itemName = ""
                mapPoint =
                    MapPoint.mapPointWithGeoCoord(
                        destination.heritageLat.toDouble(),
                        destination.heritageLng.toDouble()
                    )
                markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
                selectedMarkerType =
                    MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                showAnimationType = MapPOIItem.ShowAnimationType.SpringFromGround
                tag = index

                mapView.addPOIItem(this)
            }
        }
    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
    }

    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {
    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
    }
}