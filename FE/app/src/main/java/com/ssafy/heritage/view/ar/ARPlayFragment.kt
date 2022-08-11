package com.ssafy.heritage.view.ar

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.core.exceptions.*
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentARPlayBinding
import com.ssafy.heritage.helpers.ARCoreSessionLifecycleHelper
import com.ssafy.heritage.helpers.FullScreenHelper
import com.ssafy.heritage.helpers.GeoPermissionsHelper
import com.ssafy.heritage.samplerender.SampleRender

private const val TAG = "ARPlayFragment___"

class ARPlayFragment : BaseFragment<FragmentARPlayBinding>(R.layout.fragment_a_r_play) {

    override fun init() {

    }


}