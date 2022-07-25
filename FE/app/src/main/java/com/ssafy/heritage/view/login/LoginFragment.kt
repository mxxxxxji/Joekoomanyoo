package com.ssafy.heritage.view.login

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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
            val navController = findNavController()
            navController.navigate(R.id.action_loginFragment_to_joinFragment)
        }

        // 로그인 버튼 클릭
        btnLogin.setOnClickListener {

            // 로그인 성공했을 경우
            if (loginViewModel.login()) {
                // 홈 화면으로 이동
            }
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }
}