package com.ssafy.heritage.view.heritage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.databinding.FragmentHeritageInfoBinding
import com.ssafy.heritage.viewmodel.HeritageViewModel

class HeritageInfoFragment : BaseFragment<FragmentHeritageInfoBinding>(R.layout.fragment_heritage_info) {

    private val viewModel: HeritageViewModel by activityViewModels()
    private var heritage: Heritage? = null

    override fun init() {
        initObserver()
    }

    private fun initObserver() {
        viewModel.heritage.observe(viewLifecycleOwner) {
            Log.d("blah blah", it.toString())
            binding.heritage = it
        }
    }

}