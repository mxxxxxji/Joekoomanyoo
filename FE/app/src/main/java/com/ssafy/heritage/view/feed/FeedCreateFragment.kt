package com.ssafy.heritage.view.feed

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Feed
import com.ssafy.heritage.databinding.FragmentFeedCreateBinding
import com.ssafy.heritage.viewmodel.FeedViewModel

private const val TAG = "FeedCreateFragment___"

class FeedCreateFragment : BaseFragment<FragmentFeedCreateBinding>(R.layout.fragment_feed_create) {

    val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()
    private val feedViewModel by activityViewModels<FeedViewModel>()
    private lateinit var feedInfo: Feed
    private var imageUrl: String = ""
    private var hashTag: String = ""
    private var content: String = ""
    private var feedOpen: Char = 'Y'

    override fun init() {
    }

    private fun initObserver() {
        feedViewModel.insertFeedInfo.observe(viewLifecycleOwner) {
            if (it != null) {
                makeToast("피드가 등록되었습니다.")
                val action = FeedCreateFragmentDirections.actionFeedCreateFragmentToFeedDetailFragment()
                findNavController().navigate(action)
            } else {
                makeToast("피드 등록에 실패했습니다. 다시 확인해주세요.")
            }
        }
    }

    private fun initClickListener() = with(binding) {
        // 공개/비공개 여부
        imagebtnFeedCreateLock.setOnClickListener {  }
        // 피드 등록하기 버튼 클릭 시
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }
}