package com.ssafy.heritage.view.login

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentJoinBinding
import com.ssafy.heritage.viewmodel.JoinViewModel

private const val TAG = "JoinFragment___"

class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join) {

    private val joinViewModel by activityViewModels<JoinViewModel>()

    override fun init(): Unit = with(binding) {
        joinVM = joinViewModel

        initObserver()

        initClickListener()

        setTextChangedListener()

        initView()
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
            Log.d(TAG, "initClickListener: ${joinViewModel.gender.value}")

            // 닉네임 중복검사 했는지 확인
            if (joinViewModel.isCheckedNickname.value == false) {
                makeTextInputLayoutError(tilNickname, "닉네임 중복검사를 해주세요")
                makeToast("닉네임 중복검사를 해주세요")
                return@setOnClickListener
            }

            // 비밀번호 유효성 검사 했는지 확인
            if (!joinViewModel.validatePw(tilPw, tilPwCheck)) {
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
                // 로그인 화면으로 이동
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

        // 나이 선택 범위 지정
        npYear.apply {
            val birthList = (9..100).toList().map { it.toString() }
            minValue = 9
            maxValue = birthList.size - 1
            wrapSelectorWheel = false
            displayedValues = birthList.toTypedArray()
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