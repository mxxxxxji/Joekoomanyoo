package com.ssafy.heritage.view.heritage

import android.Manifest
import android.view.View
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.HeritageListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.databinding.FragmentHeritageListBinding
import com.ssafy.heritage.databinding.PopupHeritageSortBinding
import com.ssafy.heritage.listener.HeritageListClickListener
import com.ssafy.heritage.viewmodel.HeritageViewModel


private const val TAG = "HeritageListFragment___"
private val PERMISSIONS_REQUIRED = Manifest.permission.ACCESS_FINE_LOCATION


class HeritageListFragment :
    BaseFragment<FragmentHeritageListBinding>(R.layout.fragment_heritage_list) {

    private val heritageAdapter: HeritageListAdapter by lazy { HeritageListAdapter() }
    private val heritageViewModel by activityViewModels<HeritageViewModel>()

    private lateinit var popupWindow: PopupWindow

    override fun init() {

        initAdapter()

        initObserver()

        setPopupWindow()

        setToolbar()

        setSearchView()
    }

    private fun initAdapter() = with(binding) {
        recyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = heritageAdapter

            heritageAdapter.heritageListClickListener = object : HeritageListClickListener {
                override fun onClick(position: Int, heritage: Heritage, view: View) {

                    heritageViewModel.setHeritage(heritage)

                    // 해당 문화유산의 상세페이지로 이동
                    parentFragmentManager
                        .beginTransaction()
                        .addSharedElement(view, "heritage")
                        .addToBackStack(null)
                        .replace(
                            R.id.fragment_container_main,
                            HeritageDetailFragment.newInstance(heritage)
                        )
                        .commit()
                }
            }
        }
    }

    private fun initObserver() {
        heritageViewModel.heritageList.observe(viewLifecycleOwner) {
            heritageAdapter.submitList(it)
        }

        binding.headerHeritage.setOnClickListener {
            popupWindow.showAsDropDown(it)
        }
    }

    private fun setPopupWindow() {
        val popBinding = PopupHeritageSortBinding.inflate(layoutInflater)

        // 리뷰순 정렬 클릭시
        popBinding.btnSortReview.setOnClickListener {
            // 우선 리뷰 내림차순, 그 다음 seq 오름차순
            heritageAdapter.submitList(
                heritageViewModel.heritageList.value!!.sortedWith(
                    compareBy({ -it.heritageReviewCnt },
                        { it.heritageSeq })
                )
            )
            popupWindow.dismiss()
        }

        // 스크랩순 정렬 클릭시
        popBinding.btnSortScrap.setOnClickListener {
            // 우선 스크랩 내림차순, 그 다음 seq 오름차순
            heritageAdapter.submitList(
                heritageViewModel.heritageList.value!!.sortedWith(
                    compareBy({ -it.heritageScrapCnt },
                        { it.heritageSeq })
                )
            )
            popupWindow.dismiss()
        }

        // 거리순 정렬 클릭시
        popBinding.btnSortDist.setOnClickListener {
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
                    val newList = heritageViewModel.heritageList.value!!.filter {
                        it.heritageName.contains(query)
                    }
                    heritageAdapter.submitList(newList)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    heritageAdapter.submitList(heritageViewModel.heritageList.value!!)
                }
                return false
            }
        })
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
}