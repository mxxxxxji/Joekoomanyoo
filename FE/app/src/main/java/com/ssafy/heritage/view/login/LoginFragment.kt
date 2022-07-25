package com.ssafy.heritage.view.login

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentLoginBinding
import com.ssafy.heritage.viewmodel.LoginViewModel

private const val TAG = "LoginFragment___"

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun init() = with(binding) {
        loginVM = loginViewModel

        initObserve()

        initClickListener()

    }

    private fun initObserve() {
        loginViewModel.message.observe(viewLifecycleOwner) {

            // viewModel에서 Toast메시지 Event 발생시
            it.getContentIfNotHandled()?.let {
                makeToast(it)
            }
        }
    }

    private fun initClickListener() = with(binding) {
        // 회원가입 버튼 클릭
        btnJoin.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_login, JoinFragment())
                .commit()
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }
}