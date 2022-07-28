package com.ssafy.heritage.view.heritage

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.HeritageListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.databinding.FragmentHeritageListBinding
import com.ssafy.heritage.listener.HeritageListClickListener
import com.ssafy.heritage.viewmodel.HeritageViewModel

private const val TAG = "HeritageListFragment___"

class HeritageListFragment :
    BaseFragment<FragmentHeritageListBinding>(R.layout.fragment_heritage_list) {

    private val heritageAdapter: HeritageListAdapter by lazy { HeritageListAdapter() }
    private val heritageViewModel by viewModels<HeritageViewModel>()

    override fun init() {

        initAdapter()

        initObserver()
    }

    override fun onStart() {
        super.onStart()
        // 나중에 서버 연결하면 사용할거임
//        heritageViewModel.getHeritageList()
    }

    private fun initAdapter() = with(binding) {
        recyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = heritageAdapter

            heritageAdapter.heritageListClickListener = object : HeritageListClickListener {
                override fun onClick(position: Int, heritage: Heritage, view: View) {
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
        // 더미데이터 집어넣었음
        heritageAdapter.submitList(list)
    }

    private fun initObserver() {
        heritageViewModel.heritageList.observe(viewLifecycleOwner) {
            // 나중에 서버 연결하면 사용할거임
//            heritageAdapter.submitList(it)
        }
    }
}

// 더미데이터임
private val list = arrayListOf<Heritage>(
    Heritage(
        1,
        "서울 숭례문",
        "조선 태조 7년(1398)",
        "서울 중구 세종대로 40 (남대문로4가)",
        "유적건조물",
        "126.975312652739",
        "37.559975221378",
        "http://www.cha.go.kr/unisearch/images/national_treasure/2685609.jpg",
        "조선시대 한양도성의 정문으로",
        "",
        'Y',
        "국보",
        0,
        0
    ),
    Heritage(
        1,
        "서울 숭례문",
        "조선 태조 7년(1398)",
        "서울 중구 세종대로 40 (남대문로4가)",
        "유적건조물",
        "126.975312652739",
        "37.559975221378",
        "http://www.cha.go.kr/unisearch/images/national_treasure/2685609.jpg",
        "조선시대 한양도성의 정문으로",
        "",
        'Y',
        "국보",
        0,
        0
    )
)