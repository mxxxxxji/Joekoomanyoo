package com.ssafy.heritage.view.login

import androidx.fragment.app.activityViewModels
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentJoinBinding
import com.ssafy.heritage.viewmodel.JoinViewModel

private const val TAG = "JoinFragment___"

class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join) {

    private val joinViewModel by activityViewModels<JoinViewModel>()

    private fun initView() = with(binding) {
    }

    override fun init() = with(binding) {
        joinVM = joinViewModel
    }

}