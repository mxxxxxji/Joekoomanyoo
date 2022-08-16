package com.ssafy.heritage.view

import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.afdhal_fa.imageslider.model.SlideUIModel
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.GroupMyListAdapter
import com.ssafy.heritage.adpter.HomeFeedAdapter
import com.ssafy.heritage.adpter.HomeHeritageAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.remote.response.FeedListResponse
import com.ssafy.heritage.databinding.FragmentHomeBinding
import com.ssafy.heritage.listener.FeedListClickListener
import com.ssafy.heritage.listener.HomeCategoryListClickListener
import com.ssafy.heritage.view.feed.FeedDetailFragment
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

    private val homeHeritageAnimationAdapter: ScaleInAnimationAdapter by lazy {
        ScaleInAnimationAdapter(homeHeritageAdapter).apply {
            setDuration(2000)
            setInterpolator(OvershootInterpolator())
            setFirstOnly(false)
        }
    }

    private val homeFeedAnimationAdapter: ScaleInAnimationAdapter by lazy {
        ScaleInAnimationAdapter(homeFeedAdapter).apply {
            setDuration(2000)
            setInterpolator(OvershootInterpolator())
            setFirstOnly(false)
        }
    }

    private val myGroupAnimationAdapter: ScaleInAnimationAdapter by lazy {
        ScaleInAnimationAdapter(myGroupListAdapter).apply {
            setDuration(2000)
            setInterpolator(OvershootInterpolator())
            setFirstOnly(false)
        }
    }

    val imageList = ArrayList<SlideUIModel>().apply {
        add(
            SlideUIModel(
                "data:image/png;base64,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",
                "Blackpink - Jennie"
            )
        )
        add(
            SlideUIModel(
                "data:image/png;base64,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",
                "Blackpink - Lisa"
            )
        )
        add(
            SlideUIModel(
                "data:image/png;base64,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",
                "Blackpink - Rose"
            )
        )
        add(
            SlideUIModel(
                "data:image/png;base64,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",
                "Blackpink - Jisoo"
            )
        )
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

//        heritageViewModel.heritageList.observe(viewLifecycleOwner) { list ->
//            // 스크랩 순으로 문화재 추천
//            homeHeritageAdapter.submitList(
//                list.sortedWith(
//                    compareBy({ -it.heritageScrapCnt },
//                        { it.heritageSeq })
//                )
//            )
//        }

        userViewModel.user.observe(viewLifecycleOwner) {
            binding.user = it
        }

        groupViewModel.myGroupList.observe(viewLifecycleOwner) {
            myGroupListAdapter.submitList(it.filter { it.groupActive == 'Y' && it.groupStatus != 'F' && it.memberStatus != 0 })
        }

        feedViewModel.feedListAll.observe(viewLifecycleOwner) {
            homeFeedAdapter.submitList(it)
        }
    }

    private fun initAdapter() = with(binding) {

        // 나의 모임 리스트
//        recyclerviewMyGroup.apply {
//            adapter = myGroupAnimationAdapter
//            layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//
//            myGroupListAdapter.groupMyListClickListener = object : GroupMyListClickListener {
//                override fun onClick(position: Int, group: MyGroupResponse) {
//                    val data = GroupListResponse(
//                        group.groupSeq,
//                        group.groupName,
//                        group.groupImgUrl,
//                        group.groupMaster,
//                        group.groupDescription,
//                        group.groupAccessType,
//                        group.groupPassword,
//                        group.groupMaxCount,
//                        group.groupRegion,
//                        group.groupStartDate,
//                        group.groupEndDate,
//                        group.groupAgeRange,
//                        group.groupWithChild,
//                        group.groupWithGlobal,
//                        group.groupActive,
//                        group.groupStatus
//                    )
//                    val action =
//                        HomeFragmentDirections.actionHomeFragmentToGroupInfoFragment(data)
//                    findNavController().navigate(action)
//                }
//            }
//        }

        // 문화재 카테고리
        recyclerviewHeritage.apply {
            adapter = homeHeritageAnimationAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 5)

            val list = arrayListOf("탑", "비", "불교", "공예품", "궁궐", "기록유산", "왕릉", "건축", "종", "기타")
            homeHeritageAdapter.submitList(list)

            homeHeritageAdapter.homeCategoryListClickListener =
                object : HomeCategoryListClickListener {
                    override fun onClick(position: Int) {
                        val bundle = Bundle().apply {
                            putInt("id", position + 1)
                        }
                        findNavController().navigate(
                            R.id.action_homeFragment_to_heritageListFragment,
                            bundle
                        )
                    }
                }

//            homeHeritageAdapter.heritageListClickListener = object : HeritageListClickListener {
//                override fun onClick(position: Int, heritage: Heritage, view: View) {
//
//                    heritageViewModel.setHeritage(heritage)
//
//                    // 해당 문화유산의 상세페이지로 이동
//                    parentFragmentManager
//                        .beginTransaction()
//                        .addSharedElement(view, "heritage")
//                        .addToBackStack(null)
//                        .replace(
//                            R.id.fragment_container_main,
//                            HeritageDetailFragment.newInstance(heritage)
//                        )
//                        .commit()
//                }
//            }
        }

        // 추천 사진 리스트
        recyclerviewFeed.apply {
            adapter = homeFeedAnimationAdapter
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
//        tvGoMyGroup.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_groupListFragment)
//        }

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