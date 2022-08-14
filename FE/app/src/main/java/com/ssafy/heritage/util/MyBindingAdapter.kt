package com.ssafy.heritage.util

import android.content.res.Resources
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ssafy.heritage.R
import com.ssafy.heritage.util.DateFormatter.formatChatDate
import kotlin.coroutines.coroutineContext

@BindingAdapter("glide")
fun setImage(v: ImageView, url: String) {
    if (!url.isNullOrEmpty() && url != "0" && url != "null") {
        Glide.with(v.context)
            .load(url)
            .into(v)
    } else{
        Glide.with(v.context)
            .load(R.drawable.image_ready)
            .into(v)
    }
}

@BindingAdapter("setTime")
fun setTime(v: TextView, s: String) {
    v.text = s.formatChatDate()
}
