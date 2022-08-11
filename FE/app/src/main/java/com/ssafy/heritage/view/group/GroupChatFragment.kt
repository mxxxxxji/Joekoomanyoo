package com.ssafy.heritage.view.group

import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.END_ICON_CUSTOM
import com.google.android.material.textfield.TextInputLayout.END_ICON_NONE
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentGroupChatBinding

private const val TAG = "GroupChatFragment___"

class GroupChatFragment : BaseFragment<FragmentGroupChatBinding>(R.layout.fragment_group_chat) {
    override fun init() {

        initView()

        setTextChangedListener()
    }

    private fun initView() = with(binding) {
        tilChat.requestFocus()
    }

    private fun setTextChangedListener() = with(binding) {

        // id 이메일 입력창 에러 비활성화
        tilChat.editText?.addTextChangedListener {
            tilChat.isErrorEnabled = false
            if (it?.length ?: 0 < 1) {
                tilChat.endIconMode = END_ICON_NONE
            } else {
                tilChat.endIconMode = END_ICON_CUSTOM
            }
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