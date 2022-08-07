package com.ssafy.heritage.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.ssafy.heritage.R
import com.ssafy.heritage.databinding.DialogOtherProfileBinding

class OtherProfileDialog(context: Context, otherProfileDialogInterface: OtherProfileDialogInterface):Dialog(context) {

    private lateinit var binding: DialogOtherProfileBinding
    private val otherProfileDialogInterface: OtherProfileDialogInterface = otherProfileDialogInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_other_profile, null, false)
        setContentView(binding.root)

        // 배경 투명하게 바꿔줌
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initClickListener()
    }
    private fun initClickListener() = with(binding){
        btnApprove.setOnClickListener {  }
    }
}