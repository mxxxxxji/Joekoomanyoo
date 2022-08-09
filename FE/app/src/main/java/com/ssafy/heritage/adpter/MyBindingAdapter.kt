package com.ssafy.heritage.adpter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ssafy.heritage.ApplicationClass

object MyBindingAdapter {

    // 프로필 이미지 바인딩
    @BindingAdapter("image")
    @JvmStatic
    fun ImageView.setImage (imageUrl: String?){
        Glide.with(this.context)
            .load("${ApplicationClass.IMG_URL}${imageUrl}")
            .circleCrop()
            .into(this)
    }
}