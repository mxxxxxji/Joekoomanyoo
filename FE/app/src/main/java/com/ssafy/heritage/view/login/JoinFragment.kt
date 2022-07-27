package com.ssafy.heritage.view.login

import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentJoinBinding
import com.ssafy.heritage.viewmodel.JoinViewModel

private const val TAG = "JoinFragment___"

class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join) {

    private val joinViewModel by viewModels<JoinViewModel>()

    // 소셜로그인인지 아닌지 확인
    private val type by lazy { arguments?.getString("type") ?: "" }

    override fun init(): Unit = with(binding) {
        joinVM = joinViewModel

        initObserver()

        initView()

        initClickListener()

        setTextChangedListener()
    }

    private fun initObserver() {
        joinViewModel.message.observe(viewLifecycleOwner) {

            // viewModel에서 Toast메시지 Event 발생시
            it.getContentIfNotHandled()?.let {
                makeToast(it)
            }
        }
    }

    private fun initClickListener() = with(binding) {

        // 인증번호전송 클릭시
        btnRequestIdVertification.setOnClickListener {

            // 인증번호 요청 성공시
            if (joinViewModel.sendIdVeroficationCode(tilId)) {
                tilIdVertification.visibility = View.VISIBLE
                btnIdVertify.visibility = View.VISIBLE
            } else {

            }
        }

        // 인증하기 클릭시
        btnIdVertify.setOnClickListener {

            // 인증번호 인증 성공시
            if (joinViewModel.idVerify(tilIdVertification)) {
                tilId.isEnabled = false
                btnRequestIdVertification.isEnabled = false
                tilIdVertification.editText?.setText("인증성공")
                tilIdVertification.isEnabled = false
                btnIdVertify.isEnabled = false

                motionlayout.transitionToEnd()
            }
        }

        // 닉네임 중복검사 클릭시
        btnRequestNicknameVertification.setOnClickListener {
            joinViewModel.nicknameVerify(tilNickname)
        }

        // 회원가입 클릭시
        btnJoin.setOnClickListener {

            //

            // 닉네임 중복검사 했는지 확인
            if (joinViewModel.isCheckedNickname.value == false) {
                makeTextInputLayoutError(tilNickname, "닉네임 중복검사를 해주세요")
                makeToast("닉네임 중복검사를 해주세요")
                return@setOnClickListener
            }

            // 비밀번호 유효성 검사 했는지 확인 (소셜 회원가입이 아닌 경우)
            if (!type.equals("social") && !joinViewModel.validatePw(tilPw, tilPwCheck)) {
                return@setOnClickListener
            }

            // 생년월일 선택했는지 확인

            // 성별 선택했는지 확인인
            if (joinViewModel.gender.value == null) {
                makeToast("성별을 선택해주세요")
                return@setOnClickListener
            }

            // 유효성 검사 다 통과하면 회원가입 요청
            // 회원가입 성공시
            if (joinViewModel.join()) {

                // 소셜 회원가입시 홈으로 바로이동
                if (type.equals("social")) {
                    makeToast("회원가입성공")
                }
                // 일반 회원가입시 로그인 화면으로 이동
                else {
                    findNavController().navigate(R.id.action_joinFragment_to_loginFragment)
                }

            } else {

            }
        }
    }

    private fun setTextChangedListener() = with(binding) {

        // id 이메일 입력창 에러 비활성화
        tilId.editText?.addTextChangedListener {
            tilId.isErrorEnabled = false
        }

        // 이메일 인증창 에러 비활성화
        tilIdVertification.editText?.addTextChangedListener {
            tilIdVertification.isErrorEnabled = false
        }

        // 닉네임 입력창 에러 비활성화
        tilNickname.editText?.addTextChangedListener {
            tilNickname.isErrorEnabled = false
        }

        // 비밀번호 입력창 에러 비활성화
        tilPw.editText?.addTextChangedListener {
            tilPw.isErrorEnabled = false
        }

        // 비밀번호 재확인 입력창 에러 비활성화
        tilPwCheck.editText?.addTextChangedListener {
            tilPwCheck.isErrorEnabled = false
        }
    }

    private fun initView() = with(binding) {

        // 소셜 로그인인 경우
        if (type.equals("social")) {
            tilPw.visibility = View.GONE
            tilPwCheck.visibility = View.GONE
            motionlayout.transitionToEnd()
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    fun makeTextInputLayoutError(textInputLayout: TextInputLayout, msg: String) {
        textInputLayout.error = msg
        textInputLayout.isErrorEnabled = true
    }
}