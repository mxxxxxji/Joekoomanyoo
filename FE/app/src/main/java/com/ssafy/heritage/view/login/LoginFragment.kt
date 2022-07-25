package com.ssafy.heritage.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ssafy.heritage.R
import com.ssafy.heritage.databinding.FragmentLoginBinding
import com.ssafy.heritage.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private val binding: FragmentLoginBinding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        observe()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun observe() {
        loginViewModel.message.observe(viewLifecycleOwner) {

            // viewModel에서 Toast메시지 Event 발생시
            it.getContentIfNotHandled()?.let {
                makeToast(it)
            }
        }
    }

    private fun initView() = with(binding) {
        loginVM = loginViewModel

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