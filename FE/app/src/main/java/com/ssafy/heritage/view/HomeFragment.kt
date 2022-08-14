package com.ssafy.heritage.view

import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afdhal_fa.imageslider.model.SlideUIModel
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.GroupMyListAdapter
import com.ssafy.heritage.adpter.HomeFeedAdapter
import com.ssafy.heritage.adpter.HomeHeritageAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.data.remote.response.FeedListResponse
import com.ssafy.heritage.databinding.FragmentHomeBinding
import com.ssafy.heritage.listener.FeedListClickListener
import com.ssafy.heritage.listener.HeritageListClickListener
import com.ssafy.heritage.view.feed.FeedDetailFragment
import com.ssafy.heritage.view.heritage.HeritageDetailFragment
import com.ssafy.heritage.viewmodel.FeedViewModel
import com.ssafy.heritage.viewmodel.GroupViewModel
import com.ssafy.heritage.viewmodel.HeritageViewModel
import com.ssafy.heritage.viewmodel.UserViewModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

private const val TAG = "HomeFragment__"

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val myGroupListAdapter: GroupMyListAdapter by lazy { GroupMyListAdapter() }
    private val homeHeritageAdapter: HomeHeritageAdapter by lazy { HomeHeritageAdapter() }
    private val homeFeedAdapter: HomeFeedAdapter by lazy { HomeFeedAdapter() }

    private val heritageViewModel by activityViewModels<HeritageViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()
    private val groupViewModel by activityViewModels<GroupViewModel>()
    private val feedViewModel by activityViewModels<FeedViewModel>()

    private val scaleInAnimationAdapter: ScaleInAnimationAdapter by lazy {
        ScaleInAnimationAdapter(homeHeritageAdapter).apply {
            setDuration(2000)
            setInterpolator(OvershootInterpolator())
            setFirstOnly(false)
        }
    }

    val imageList = ArrayList<SlideUIModel>().apply {
        add(SlideUIModel("https://s.id/Ccoeo", "Blackpink - Jennie"))
        add(SlideUIModel("https://s.id/CcouZ", "Blackpink - Lisa"))
        add(SlideUIModel("https://s.id/CcoQ1", "Blackpink - Rose"))
        add(SlideUIModel("https://s.id/Cco-g", "Blackpink - Jisoo"))
    }


    override fun init() {

        initView()

        initObserver()

        initAdapter()

        initClickListener()

        setToolbar()
    }

    private fun initView() = with(binding) {
        imageSlide.setImageList(imageList)
    }

    private fun initObserver() {

        heritageViewModel.heritageList.observe(viewLifecycleOwner) { list ->
            // 스크랩 순으로 문화재 추천
            homeHeritageAdapter.submitList(
                list.sortedWith(
                    compareBy({ -it.heritageScrapCnt },
                        { it.heritageSeq })
                )
            )
        }

        userViewModel.user.observe(viewLifecycleOwner) {
            binding.user = it
        }

        groupViewModel.myGroupList.observe(viewLifecycleOwner) {
            myGroupListAdapter.submitList(it.filter { it.groupStatus != 'F' && it.memberStatus != 0 })
        }

        feedViewModel.feedListAll.observe(viewLifecycleOwner) {
            homeFeedAdapter.submitList(it)
        }
    }

    private fun initAdapter() = with(binding) {

        // 나의 모임 리스트
        recyclerviewMyGroup.apply {
            adapter = myGroupListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        // 추천 문화재 리스트
        recyclerviewHeritage.apply {
            adapter = scaleInAnimationAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            homeHeritageAdapter.heritageListClickListener = object : HeritageListClickListener {
                override fun onClick(position: Int, heritage: Heritage, view: View) {

                    heritageViewModel.setHeritage(heritage)

                    // 해당 문화유산의 상세페이지로 이동
                    parentFragmentManager
                        .beginTransaction()
                        .addSharedElement(view, "heritage")
                        .addToBackStack(null)
                        .replace(
                            R.id.fragment_container_main,
                            HeritageDetailFragment.newInstance(heritage)
                        )
                        .commit()
                }
            }
        }

        // 추천 사진 리스트
        recyclerviewFeed.apply {
            adapter = homeFeedAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            homeFeedAdapter.feedListClickListener = object : FeedListClickListener {
                override fun onClick(position: Int, feed: FeedListResponse, view: View) {
                    parentFragmentManager
                        .beginTransaction()
                        .addSharedElement(view, "feed")
                        .addToBackStack(null)
                        .replace(
                            R.id.fragment_container_main,
                            FeedDetailFragment.newInstance(feed)
                        )
                        .commit()
                }
            }
        }
    }

    private fun initClickListener() = with(binding) {

        // 나의 모임 더보기 클릭시
        tvGoMyGroup.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_groupListFragment)
        }

        // 추천 문화재 더보기 클릭시
        tvGoHeritage.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_heritageListFragment)
        }

        // 추천 사진 더보기 클릭시
        tvGoFeed.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_feedListFragment)
        }
    }

    private fun setToolbar() = with(binding) {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                // 프로필 메뉴 클릭시
                R.id.profile -> {
                    findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
                    true
                }
                // 알림 메뉴 클릭시
                R.id.notification -> {
                    findNavController().navigate(R.id.action_homeFragment_to_notiFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}