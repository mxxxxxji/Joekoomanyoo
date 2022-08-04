package com.ssafy.heritage.view.heritage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.data.remote.api.UserService
import com.ssafy.heritage.databinding.FragmentHeritageDetailBinding


private const val TAG = "HeritageDetailFragment___"
private const val ARG_HERITAGE = "heritage"

class HeritageDetailFragment :
    BaseFragment<FragmentHeritageDetailBinding>(R.layout.fragment_heritage_detail) {

    private var heritage: Heritage? = null
    private lateinit var heritageInfoFragment: HeritageInfoFragment
    private lateinit var heritageReviewFragment: HeritageReviewFragment
    private lateinit var userService: UserService

    @SuppressLint("LongLogTag")
    override fun init() {
        initAdapter()
        arguments?.let {
            heritage = it.getSerializable(ARG_HERITAGE) as Heritage
            Log.d(TAG, "init: $heritage")

            binding.heritage = heritage
        }

    // 스크랩처리
//    override suspend fun like(reviewId: Int) {
//
//        if (userDao.hasLike(reviewId) > 0) {
//            // 스크랩 있다면 제거
//            //api 호출
//            userService.deleteHeritageScrap(
//                Like(
//                    reviewId = reviewId,
//                    user_id = userId(),
//                    like_id = userDao.getLike1(reviewId).like_id
//                )
//            )
//            //local db 처리
//            userDao.deleteLike(Like(reviewId = reviewId))
//        } else {
//            // 스크랩 없다면 추가
//            val like = Like(reviewId = reviewId, user_id = userId())
//            //api 호출
//            val resultLike = userService.insertHeritageScrap(like)
//            //local db 처리
//            userDao.insertLike(resultLike)
//        }
//    }

    }
    private fun initAdapter()=with(binding) {
        heritageInfoFragment = HeritageInfoFragment()
        heritageReviewFragment = HeritageReviewFragment()

        childFragmentManager.beginTransaction()
            // heritageInfoFragment를 먼저 띄우는거다
            .replace(R.id.fragment_container_view,heritageInfoFragment)
            .commit()

        // 탭 클릭 시 이벤트
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // 몇 번째 탭을 클릭했을 때
                when(tab!!.position) {
                    0 -> replaceView(heritageInfoFragment)
                    1 -> replaceView(heritageReviewFragment)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
    }
    companion object {
        fun newInstance(param: Heritage) =
            HeritageDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_HERITAGE, param)
                }
            }
    }

    private fun replaceView(tab: Fragment) {
        // 탭한 화면 변경
        var selectedFragment: Fragment?=null
        selectedFragment = tab
        selectedFragment?.let {
            childFragmentManager?.beginTransaction()?.replace(R.id.fragment_container_view, it)?.commit()
        }
    }

}