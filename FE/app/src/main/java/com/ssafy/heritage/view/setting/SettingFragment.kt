package com.ssafy.heritage.view.setting

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awesomedialog.*
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.SettingListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentSettingBinding
import com.ssafy.heritage.listener.SettingListClickListener
import com.ssafy.heritage.util.Setting.LANGUAGE_SETTING
import com.ssafy.heritage.util.Setting.LICENSE
import com.ssafy.heritage.util.Setting.NOTI_SETTING
import com.ssafy.heritage.util.Setting.TERMS
import com.ssafy.heritage.util.Setting.VERSION_INFO


private const val TAG = "SettingFragment___"

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val settingListAdapter: SettingListAdapter by lazy { SettingListAdapter() }

    override fun init() {

        initAdapter()

    }

    private fun initAdapter() = with(binding) {

        // 설정 메뉴 리스트
        recyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = settingListAdapter

            settingListAdapter.settingListClickListener = object : SettingListClickListener {
                override fun onClick(position: Int, view: View) {
                    val name = settingListAdapter.currentList[position]
                    when (name) {
                        NOTI_SETTING -> {

                        }
                        LANGUAGE_SETTING -> {

                        }
                        VERSION_INFO -> {
                            AwesomeDialog.build(requireActivity())
                                .title(VERSION_INFO)
                                .body(BuildConfig.VERSION_NAME)
                                .icon(R.drawable.ic_launcher_foreground)
                                .onPositive(
                                    "닫기",
                                    buttonBackgroundColor = R.drawable.button_join,
                                    textColor = ContextCompat.getColor(
                                        requireContext(),
                                        android.R.color.black
                                    )
                                )
                        }
                        TERMS -> {
                        }
                        LICENSE -> {

                        }
                    }
                }
            }

            settingListAdapter.submitList(settingList)
        }
    }

    val settingList =
        arrayListOf<String>(NOTI_SETTING, LANGUAGE_SETTING, VERSION_INFO, TERMS, LICENSE)
}