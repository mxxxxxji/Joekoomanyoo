package com.ssafy.heritage.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.viewpager.widget.ViewPager


class NonSwipeViewPager : ViewPager {
    constructor(@NonNull context: Context?) : super(context!!) {}
    constructor(@NonNull context: Context?, @Nullable attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return false
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return false
    }
}