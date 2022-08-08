package com.ssafy.heritage.view.heritage

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.transition.Fade
import androidx.transition.TransitionInflater
import com.google.android.material.tabs.TabLayout
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.data.dto.HeritageScrap
import com.ssafy.heritage.data.remote.api.UserService
import com.ssafy.heritage.databinding.FragmentHeritageDetailBinding
import com.ssafy.heritage.view.HomeActivity
import com.ssafy.heritage.view.dialog.SharedMyGroupListDialog
import com.ssafy.heritage.viewmodel.HeritageViewModel
import com.ssafy.heritage.viewmodel.UserViewModel


private const val TAG = "HeritageDetailFragment___"
private const val ARG_HERITAGE = "heritage"

class HeritageDetailFragment :
    BaseFragment<FragmentHeritageDetailBinding>(R.layout.fragment_heritage_detail) {

    private var heritage: Heritage? = null
    private lateinit var heritageInfoFragment: HeritageInfoFragment
    private lateinit var heritageReviewFragment: HeritageReviewFragment
    private lateinit var userService: UserService
    private val userViewModel by activityViewModels<UserViewModel>()
    private val heritageViewModel: HeritageViewModel by activityViewModels()
    private lateinit var heritageScrap: HeritageScrap

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


    @SuppressLint("LongLogTag")
    override fun init() {

        binding.heritage = heritage

        initAdapter()
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

        arguments?.let {
            heritage = it.getSerializable(ARG_HERITAGE) as Heritage
            Log.d(TAG, "init: $heritage")
        }
    }

    override fun onDetach() {
        super.onDetach()

        (requireActivity() as HomeActivity).backPressedListener.unregister()

        // 뒤로가기 콜백 해제
        callback.remove()
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
    }


    private fun initAdapter() = with(binding) {
        heritageInfoFragment = HeritageInfoFragment()
        heritageReviewFragment = HeritageReviewFragment()

        childFragmentManager.beginTransaction()
            // heritageInfoFragment를 먼저 띄우는거다
            .replace(R.id.fragment_container_view, heritageInfoFragment)
            .commit()

        // 탭 클릭 시 이벤트
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // 몇 번째 탭을 클릭했을 때
                when (tab!!.position) {
                    0 -> replaceView(heritageInfoFragment)
                    1 -> replaceView(heritageReviewFragment)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
    }

    companion object {
        fun newInstance(param: Heritage) = HeritageDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_HERITAGE, param)
            }
        }
    }

    private fun replaceView(tab: Fragment) {
        // 탭한 화면 변경
        var selectedFragment: Fragment? = null
        selectedFragment = tab
        selectedFragment?.let {
            childFragmentManager.beginTransaction().replace(R.id.fragment_container_view, it)
                .commit()
        }
    }
}