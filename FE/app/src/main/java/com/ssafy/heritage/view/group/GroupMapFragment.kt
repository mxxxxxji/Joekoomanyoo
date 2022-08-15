package com.ssafy.heritage.view.group

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.GroupDestinationMap
import com.ssafy.heritage.databinding.FragmentGroupMapBinding
import com.ssafy.heritage.viewmodel.GroupViewModel
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

private const val TAG = "GroupMapFragment___"

class GroupMapFragment : BaseFragment<FragmentGroupMapBinding>(R.layout.fragment_group_map),
    MapView.POIItemEventListener {

    private val groupViewModel by activityViewModels<GroupViewModel>()

    private lateinit var mapView: MapView

    override fun init() {

        initMap()

        initObserver()
    }

    override fun onStart() {
        super.onStart()
        groupViewModel.getGroupDestination()
    }

    private fun initMap() = with(binding) {
        mapView = MapView(requireContext())

        // 마커 클릭 리스너 설정
        mapView.setPOIItemEventListener(
            this@GroupMapFragment
        )

        map.addView(mapView)

        mapView.setZoomLevel(12, true)
    }

    private fun initObserver() {
        groupViewModel.groupDestination.observe(viewLifecycleOwner) {
            makeGroupMarker(it as MutableList<GroupDestinationMap>)
        }
    }

    // 모임에서 설정한 마커
    private fun makeGroupMarker(list: MutableList<GroupDestinationMap>) {

        list.forEachIndexed { index, destination ->
            MapPOIItem().apply {
                itemName = destination.heritage.heritageName
                mapPoint =
                    MapPoint.mapPointWithGeoCoord(
                        destination.heritage.heritageLat.toDouble(),
                        destination.heritage.heritageLng.toDouble()
                    )

                val bitmapdraw: BitmapDrawable = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_group_location,
                    null
                ) as BitmapDrawable
                val b: Bitmap = bitmapdraw.bitmap
                val marker: Bitmap = Bitmap.createScaledBitmap(b, 50, 50, false)

                markerType = MapPOIItem.MarkerType.CustomImage          // 마커 모양 (커스텀)
                selectedMarkerType = MapPOIItem.MarkerType.CustomImage  // 클릭 시 마커 모양 (커스텀)
                customImageBitmap = marker
                customSelectedImageBitmap = marker

                showAnimationType = MapPOIItem.ShowAnimationType.DropFromHeaven
                tag = index

                mapView.addPOIItem(this)
            }
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onPOIItemSelected(p0: MapView?, poiItem: MapPOIItem?) {

        mapView.setMapCenterPointAndZoomLevel(
            MapPoint.mapPointWithGeoCoord(
                poiItem?.mapPoint?.mapPointGeoCoord?.latitude!!,
                poiItem?.mapPoint?.mapPointGeoCoord?.longitude!!
            ), 1, true
        )
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