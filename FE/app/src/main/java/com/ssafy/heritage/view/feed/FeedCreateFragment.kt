package com.ssafy.heritage.view.feed

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.remote.request.FeedAddRequest
import com.ssafy.heritage.databinding.FragmentFeedCreateBinding
import com.ssafy.heritage.viewmodel.FeedViewModel

private const val TAG = "FeedCreateFragment___"

class FeedCreateFragment : BaseFragment<FragmentFeedCreateBinding>(R.layout.fragment_feed_create) {

    val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()
    private val feedViewModel by activityViewModels<FeedViewModel>()
    private lateinit var feedInfo: FeedAddRequest
    private var imageUrl: String = ""
    private var title: String = ""
    private var hashTag: String = ""
    private var content: String = ""
    private var feedOpen: Char = 'Y'

    override fun init() {
        initObserver()
        initClickListener()
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
        switchFeedCreateLock.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // 기본값이 공개
                makeToast("공개")
            } else {
                // 비공개
                feedOpen = 'N'
                makeToast("비공개")
            }
        }

        // 해시태그랑 같이 post할 수 있게 바뀌어야 함
        // 피드 등록하기 클릭 시
        tvGroupInsert.setOnClickListener {
//            imageUrl
            title = etFeedCreateTitle.toString()
            // 해시태그는 리스트로 받게 수정해야함
            hashTag = etFeedCreateTag.toString()
            content = etFeedCreateContent.toString()

            when {
                title == "" -> {
                    makeToast("제목을 입력해주세요")
                }
                hashTag == "" -> {
                    makeToast("해시태그를 입력해주세요")
                }
                content == "" -> {
                    makeToast("내용을 입력해주세요")
                }
            }

            // imageUrl을 필수값으로 하자..사진 피드니까!!
            if (title != "") {
                feedInfo = FeedAddRequest( userSeq, imageUrl, title, content, feedOpen )
                feedViewModel.insertFeed(feedInfo)
            }
        }
        imagebtnFeedBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }
}