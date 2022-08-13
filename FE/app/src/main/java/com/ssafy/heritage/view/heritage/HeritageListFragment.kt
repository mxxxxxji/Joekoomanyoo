package com.ssafy.heritage.view.heritage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.HeritageListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.databinding.FragmentHeritageListBinding
import com.ssafy.heritage.databinding.PopupHeritageSortBinding
import com.ssafy.heritage.listener.HeritageListClickListener
import com.ssafy.heritage.viewmodel.HeritageViewModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

private const val TAG = "HeritageListFragment___"


class HeritageListFragment :
    BaseFragment<FragmentHeritageListBinding>(R.layout.fragment_heritage_list) {

    private val heritageAdapter: HeritageListAdapter by lazy { HeritageListAdapter() }
    private val heritageViewModel by activityViewModels<HeritageViewModel>()
    private val alphaInAnimationAdapter: ScaleInAnimationAdapter by lazy {
        ScaleInAnimationAdapter(heritageAdapter).apply {
            setDuration(300)
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

        setChip()
    }

    private fun setChip() = with(binding) {
        chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds[0] in 1..10) {
                heritageViewModel.setCategory(checkedIds[0])
            } else {
                heritageViewModel.setCategory(0)
            }
        }

        chipGroup.check(R.id.chip_all)
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

            heritageAdapter.heritageListClickListener = object : HeritageListClickListener {
                override fun onClick(position: Int, heritage: Heritage, view: View) {

                    heritageViewModel.setHeritage(heritage)

                    // 해당 문화유산의 상세페이지로 이동
//                    requireActivity().supportFragmentManager
//                        .commit {
//                            addSharedElement(view, "heritage")
//                            replace(
//                                R.id.fragment_container_main,
//                                HeritageDetailFragment.newInstance(heritage)
//                            )
//                            addToBackStack(null)
//                        }
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
            heritageViewModel.setSort(2)
            popupWindow.dismiss()
        }

        // 스크랩순 정렬 클릭시
        popBinding.btnSortScrap.setOnClickListener {
            heritageViewModel.setSort(1)
            popupWindow.dismiss()
        }

        // 거리순 정렬 클릭시
        popBinding.btnSortDist.setOnClickListener {
            heritageViewModel.setSort(0)
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
                    val view = requireActivity().findViewById<View>(R.id.menu_sort)
                    popupWindow.showAsDropDown(view, -200, 0)
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
                    val list = heritageViewModel.heritageList.value!!.filter {
                        it.heritageName.contains(query)
                    }
                    heritageAdapter.submitList(list)
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

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    companion object {

        private val heritageListFragment by lazy { HeritageListFragment() }

        @JvmStatic
        fun newInstance() = heritageListFragment
    }
}