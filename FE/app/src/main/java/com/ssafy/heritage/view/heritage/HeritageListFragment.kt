package com.ssafy.heritage.view.heritage

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.HeritageListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.databinding.FragmentHeritageListBinding
import com.ssafy.heritage.databinding.PopupHeritageSortBinding
import com.ssafy.heritage.listener.HeritageListClickListener
import com.ssafy.heritage.util.DividerItemDecoration
import com.ssafy.heritage.util.SORT.ASCENDING_DIST
import com.ssafy.heritage.util.SORT.ASCENDING_REVIEW
import com.ssafy.heritage.util.SORT.ASCENDING_SCRAP
import com.ssafy.heritage.viewmodel.HeritageViewModel
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter


private const val TAG = "HeritageListFragment___"
private val PERMISSIONS_REQUIRED = Manifest.permission.ACCESS_FINE_LOCATION


class HeritageListFragment :
    BaseFragment<FragmentHeritageListBinding>(R.layout.fragment_heritage_list) {

    private val heritageAdapter: HeritageListAdapter by lazy { HeritageListAdapter() }
    private val heritageViewModel by activityViewModels<HeritageViewModel>()
    private var dataList: List<Heritage> = arrayListOf()
    private var selectedSort: String = ""
    private var searchedList = listOf<Heritage>()
    private val alphaInAnimationAdapter: AlphaInAnimationAdapter by lazy {
        AlphaInAnimationAdapter(heritageAdapter).apply {
            setDuration(2000)
            setInterpolator(OvershootInterpolator())
            setFirstOnly(false)
        }
    }


    private lateinit var popupWindow: PopupWindow

    override fun init() {

        initAdapter()

        setPopupWindow()

        setToolbar()

        setSearchView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // transition 효과 일단 멈춤
        postponeEnterTransition()

        initObserver()

        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }

    private fun initAdapter() = with(binding) {
        recyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = alphaInAnimationAdapter

            addItemDecoration(DividerItemDecoration(5F, resources.getColor(R.color.link_water)))

            heritageAdapter.heritageListClickListener = object : HeritageListClickListener {
                override fun onClick(position: Int, heritage: Heritage, view: View) {

                    heritageViewModel.setHeritage(heritage)

                    // 해당 문화유산의 상세페이지로 이동
                    requireActivity().supportFragmentManager
                        .commit {
                            addToBackStack(null)
                            addSharedElement(view, "heritage")
                            replace(
                                R.id.fragment_container_main,
                                HeritageDetailFragment.newInstance(heritage)
                            )
                        }
//                    parentFragmentManager
//                        .beginTransaction()
//                        .addSharedElement(view, "heritage")
//                        .addToBackStack(null)
//                        .replace(
//                            R.id.fragment_container_main,
//                            HeritageDetailFragment.newInstance(heritage)
//                        )
//                        .commit()
                }
            }
        }
    }

    private fun initObserver() {
        heritageViewModel.heritageList.observe(viewLifecycleOwner) {

            // Fragment에 처음 진입하는 경우
            if (searchedList.isNotEmpty()) {
                heritageAdapter.submitList(searchedList)
            } else {
                if (selectedSort == "") {
                    dataList = it
                    heritageAdapter.submitList(it)
                }
                // DetailFragment에서 온 경우
                else {
                    filterList(selectedSort)
                }
            }

            // 뷰 다 불러오고나서 transition 효과 시작
            (view?.parent as ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

    private fun setPopupWindow() {
        val popBinding = PopupHeritageSortBinding.inflate(layoutInflater)

        // 리뷰순 정렬 클릭시
        popBinding.btnSortReview.setOnClickListener {
            selectedSort = ASCENDING_REVIEW
            filterList(selectedSort)
            popupWindow.dismiss()
        }

        // 스크랩순 정렬 클릭시
        popBinding.btnSortScrap.setOnClickListener {
            selectedSort = ASCENDING_SCRAP
            filterList(selectedSort)
            popupWindow.dismiss()
        }

        // 거리순 정렬 클릭시
        popBinding.btnSortDist.setOnClickListener {
            selectedSort = ASCENDING_DIST
            filterList(selectedSort)
            popupWindow.dismiss()
        }

        popupWindow = PopupWindow(
            popBinding.root,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            true
        )
    }

    private fun setToolbar() = with(binding) {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_sort -> {
                    val view = requireActivity().findViewById<View>(R.id.menu_map)
                    popupWindow.showAsDropDown(view)
                    true
                }
                R.id.menu_map -> {
                    requestPermissionLancher.launch(PERMISSIONS_REQUIRED)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun setSearchView() {

        // 검색 했을 때
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    searchedList = dataList.filter {
                        it.heritageName.contains(query)
                    }
                    heritageAdapter.submitList(searchedList)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    searchedList = arrayListOf()
                    heritageAdapter.submitList(dataList)
                }
                return false
            }
        })
    }

    private fun filterList(sortType: String) {
        when (sortType) {
            ASCENDING_REVIEW -> {
                // 우선 리뷰 내림차순, 그 다음 seq 오름차순
                dataList = dataList.sortedWith(
                    compareBy({ -it.heritageReviewCnt },
                        { it.heritageSeq })
                )
                heritageAdapter.submitList(
                    dataList
                )
            }
            ASCENDING_SCRAP -> {
                // 우선 스크랩 내림차순, 그 다음 seq 오름차순
                dataList = dataList.sortedWith(
                    compareBy({ -it.heritageScrapCnt },
                        { it.heritageSeq })
                )
                heritageAdapter.submitList(
                    dataList
                )
            }
            ASCENDING_DIST -> {
                /*
                거리순 정렬 해야됨
                 */
            }
        }
    }

    // 위치 권한 체크 해주고 이후 동작 설정
    private val requestPermissionLancher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // PERMISSION GRANTED
                findNavController().navigate(R.id.action_heritageListFragment_to_heritageMapFragment)
            } else {
                // PERMISSION NOT GRANTED
                makeToast("위치 권한이 필요합니다")
            }
        }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    companion object {

        private val heritageListFragment by lazy { HeritageListFragment() }

        @JvmStatic
        fun newInstance() = heritageListFragment
    }
}