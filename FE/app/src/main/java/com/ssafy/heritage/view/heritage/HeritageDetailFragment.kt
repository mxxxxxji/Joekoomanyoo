package com.ssafy.heritage.view.heritage

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.transition.Fade
import androidx.transition.TransitionInflater
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.HeritageReviewAdapter
import com.ssafy.heritage.adpter.OnItemClickListener
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.data.dto.HeritageScrap
import com.ssafy.heritage.data.remote.api.UserService
import com.ssafy.heritage.data.remote.response.HeritageReviewListResponse
import com.ssafy.heritage.databinding.FragmentHeritageDetail2Binding
import com.ssafy.heritage.view.HomeActivity
import com.ssafy.heritage.view.dialog.SharedMyGroupListDialog
import com.ssafy.heritage.viewmodel.HeritageViewModel
import com.ssafy.heritage.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


private const val TAG = "HeritageDetailFragment___"
private const val ARG_HERITAGE = "heritage"
private val PERMISSIONS_REQUIRED = Manifest.permission.ACCESS_FINE_LOCATION

class HeritageDetailFragment :
    BaseFragment<FragmentHeritageDetail2Binding>(R.layout.fragment_heritage_detail2),
    MapView.POIItemEventListener, OnItemClickListener {
    private lateinit var mapView: MapView

    private var heritage: Heritage? = null

    private lateinit var userService: UserService
    private val userViewModel by activityViewModels<UserViewModel>()
    private val heritageViewModel by activityViewModels<HeritageViewModel>()
    private lateinit var heritageScrap: HeritageScrap

    private lateinit var heritageReview: HeritageReviewListResponse
    private lateinit var heritageReviewAdapter: HeritageReviewAdapter

    private lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity() as HomeActivity).backPressedListener.register()

        // fragment에서 back버튼 조작하도록 콜백 등록
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()

        (requireActivity() as HomeActivity).backPressedListener.unregister()

        // 뒤로가기 콜백 해제
        callback.remove()
    }


    @SuppressLint("LongLogTag")
    override fun init() {

        // 전달 받은 문화유산 데이터 받기
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main) {
                arguments?.let {
                    heritage = it.getSerializable(ARG_HERITAGE) as Heritage
                    Log.d(TAG, "init onCreate: $heritage")
                }
            }
            binding.heritage = this@HeritageDetailFragment.heritage
            Log.d(TAG, "init init: ${this@HeritageDetailFragment.heritage}")
        }

        initMap()

        initAdapter()

        initObserver()

        initClickListener()
    }

    @SuppressLint("LongLogTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        enterTransition = Fade()
        exitTransition = Fade()
    }

    override fun onStart() {
        super.onStart()
        heritageViewModel.getHeritageReviewList()
    }

    private fun initAdapter() = with(binding) {
        heritageReviewAdapter = HeritageReviewAdapter(this@HeritageDetailFragment)
        binding.recyclerviewReviewList.adapter = heritageReviewAdapter
    }

    private fun initObserver() {
        heritageViewModel.heritageReviewList.observe(viewLifecycleOwner) {
            heritageReviewAdapter.submitList(it)
        }
    }

    private fun initMap() = with(binding) {
        mapView = MapView(requireContext())

        // 마커 클릭 리스너 설정
        mapView.setPOIItemEventListener(
            this@HeritageDetailFragment
        )

        map.addView(mapView)
        requestLocationPermissionLancher.launch(PERMISSIONS_REQUIRED)
    }

    private val requestLocationPermissionLancher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // PERMISSION GRANTED

                // 문화유산 위치로 중심점 변경
                setLocation(
                    heritage?.heritageLat!!.toDouble(),
                    heritage?.heritageLng!!.toDouble(),
                    0
                )

                // 전체 문화유산 마커 찍기
                makeMarker()

            } else {
                // PERMISSION NOT GRANTED
                makeToast("위치 권한이 필요합니다")
            }
        }

    private fun setLocation(lat: Double, lng: Double, zoomLevel: Int) {
        mapView.setMapCenterPointAndZoomLevel(
            MapPoint.mapPointWithGeoCoord(
                lat,
                lng
            ), zoomLevel, true
        )
    }

    private fun makeMarker() {

        heritageViewModel.heritageList.value?.forEachIndexed { index, heritage ->
            MapPOIItem().apply {
                itemName = heritageViewModel.heritageList.value?.get(index)?.heritageName
                mapPoint =
                    MapPoint.mapPointWithGeoCoord(
                        heritage.heritageLat.toDouble(),
                        heritage.heritageLng.toDouble()
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

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    @SuppressLint("LongLogTag")
    private fun initClickListener() = with(binding) {

        // 스크랩 버튼
        // 포함 여부 확인은 여기서 (any!!)
        var scrapCheck = userViewModel.scrapList.value?.any {
            it.heritageSeq == heritage?.heritageSeq
        }

        btnScrap.setOnClickListener {

            heritageScrap = HeritageScrap(
                heritageSeq = heritage?.heritageSeq!!,
                heritageScrapSeq = 0,
                userSeq = userViewModel.user.value?.userSeq!!
            )

            // scrapList 안에 현재 heritageSeq 포함 여부 확인
            Log.d(TAG, "initClickListener: ${scrapCheck}")
            if (scrapCheck == true) {
                // 포함되어 있으면 스크랩 된 것 => 스크랩 삭제할 수 있는 동작
                userViewModel.deleteHeritageScrap(heritage?.heritageSeq!!)
                scrapCheck = false
            } else {
                // 없으면 스크랩 할 수 있는 동작
                userViewModel.insertHeritageScrap(heritageScrap)
                scrapCheck = true
            }
        }

//        // 공유하기 버튼 (SNS 공휴하기 하고 있었음,,,)
//        btnLink.setOnClickListener {
//            try {
//                val sendText = "바뀌나 함 보자"
//                var url = "heritage://heritage/detail"
//                val sendIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                sendIntent.action = Intent.ACTION_SEND
//                sendIntent.putExtra(Intent.EXTRA_TEXT, sendText)
//                sendIntent.type = "text/plain"
//                startActivity(Intent.createChooser(sendIntent, "Share"))
//            } catch (ignored: ActivityNotFoundException) {
//                Log.d("test", "ignored : $ignored")
//            }
//        }

        // 스터디로 공유하기
        btnLink.setOnClickListener {
            // 다이얼로그 띄우기
            val dialog = SharedMyGroupListDialog(heritage!!.heritageSeq)
            dialog.show(childFragmentManager, "SharedMyGroupListDialog")
        }

        // 사진 첨부 버튼 클릭 시
        btnImgAttach.setOnClickListener {
            requestStoragePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        // 리뷰 글쓰기 버튼 클릭 시
        btnCreateReview.setOnClickListener {
            if (!etReviewContent.text.isNullOrBlank()) {
                heritageReview = HeritageReviewListResponse(
                    0,
                    userSeq = userViewModel.user.value?.userSeq!!,
                    heritageSeq = heritageViewModel.heritage.value?.heritageSeq!!,
                    etReviewContent.text.toString(),
                    "",
                    0,
                    userNickname = userViewModel.user.value?.userNickname!!
                )
                heritageViewModel.insertHeritageReview(heritageReview)
                etReviewContent.setText("")
            } else {
                makeToast("리뷰를 작성해보세요")
            }
        }

        // 정보 탭 클릭시
        constraintContent1.setOnClickListener {
            motionlayout1.transitionToEnd()
            setLocation(
                heritage?.heritageLat!!.toDouble(),
                heritage?.heritageLng!!.toDouble(),
                -1
            )
        }
        constraintContent2.setOnClickListener {
            motionlayout1.transitionToStart()
            setLocation(
                heritage?.heritageLat!!.toDouble(),
                heritage?.heritageLng!!.toDouble(),
                1
            )
        }
    }

    val requestStoragePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // PERMISSION GRANTED
            pick()
        } else {
            // PERMISSION NOT GRANTED
            makeToast("권한이 거부됨")
        }
    }

    // 사진 선택
    private fun pick() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        filterActivityLauncher.launch(intent)
    }

    // 사진 골라서 가져온 결과
    private val filterActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                var currentImageUri = it.data?.data
                try {
                    currentImageUri?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                requireActivity().contentResolver,
                                currentImageUri
                            )
                            binding.btnImgAttach.setImageBitmap(bitmap)
                        } else {
                            val source = ImageDecoder.createSource(
                                requireActivity().contentResolver,
                                currentImageUri
                            )
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            binding.btnImgAttach.setImageBitmap(bitmap)
                        }
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else if (it.resultCode == Activity.RESULT_CANCELED) {
                makeToast("사진 선택 취소")
            } else {
                Log.d("ActivityResult", "something wrong")
            }
        }

    companion object {
        fun newInstance(param: Heritage) = HeritageDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_HERITAGE, param)
            }
        }
    }

    override fun onPOIItemSelected(p0: MapView?, poiItem: MapPOIItem?) {

        val data = poiItem?.tag?.let { heritageViewModel.heritageList.value?.get(it) }

        setLocation(data?.heritageLat!!.toDouble(), data?.heritageLng!!.toDouble(), -1)
        heritage = data
        binding.heritage = data
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

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }
}