package com.ssafy.heritage.view.ar

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.ssafy.heritage.HelloGeoActivity
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentARBinding

private const val TAG = "ARFragment___"

class ARFragment : BaseFragment<FragmentARBinding>(R.layout.fragment_a_r) {


    override fun init() {
        initClickListener()
    }

    private fun initClickListener() = with(binding) {

        // 뒤로가기
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // 도감보기
        btnFound.setOnClickListener {
            findNavController().navigate(R.id.action_ARFragment_to_ARInfoFragment)
        }

        // 순위보기
        btnList.setOnClickListener {
            findNavController().navigate(R.id.action_ARFragment_to_ARInfoFragment)
        }

        // 유물 찾기(카메라)
        btnPlay.setOnClickListener {
            val intent = Intent(activity, HelloGeoActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }
}