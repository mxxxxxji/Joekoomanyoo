package com.ssafy.heritage.view.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ssafy.heritage.R
import com.ssafy.heritage.databinding.FragmentJoinBinding
import com.ssafy.heritage.viewmodel.JoinViewModel

class JoinFragment : Fragment() {

    private val binding: FragmentJoinBinding by lazy { FragmentJoinBinding.inflate(layoutInflater) }
    private val joinViewModel by activityViewModels<JoinViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() = with(binding) {
        joinVM = joinViewModel
    }

}