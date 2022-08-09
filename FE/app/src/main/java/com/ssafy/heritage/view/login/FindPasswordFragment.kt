package com.ssafy.heritage.view.login

import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentFindPasswordBinding

private const val TAG = "FindPasswordFragment___"

class FindPasswordFragment :
    BaseFragment<FragmentFindPasswordBinding>(R.layout.fragment_find_password) {
    override fun init() {
        // 소셜 이메일 중복검사를 한다
        // 아이디가 없는 경우 아이디가 없다고 알림
        // FAIL social인 경우 소셜이라고 알림
        // FAIL none일 경우 이메일 인증 날림

        // 이메일 인증 성공할 경우 비밀번호 창 띄움

        // 비밀번호 제대로 입력하면 수정ㄱㄱ
    }
}