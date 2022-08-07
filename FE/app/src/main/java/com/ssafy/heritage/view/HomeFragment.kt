package com.ssafy.heritage.view

import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.GroupListAdapter
import com.ssafy.heritage.adpter.HomeHeritageAdapter
import com.ssafy.heritage.adpter.OnItemClickListener
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.databinding.FragmentHomeBinding
import com.ssafy.heritage.listener.HeritageListClickListener
import com.ssafy.heritage.view.group.GroupListFragmentDirections
import com.ssafy.heritage.view.heritage.HeritageDetailFragment
import com.ssafy.heritage.viewmodel.GroupViewModel
import com.ssafy.heritage.viewmodel.HeritageViewModel
import com.ssafy.heritage.viewmodel.UserViewModel
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

private const val TAG = "HomeFragment__"

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home),
    OnItemClickListener {

    private val groupListAdapter: GroupListAdapter by lazy { GroupListAdapter(this) }
    private val homeHeritageAdapter: HomeHeritageAdapter by lazy { HomeHeritageAdapter() }

    private val heritageViewModel by activityViewModels<HeritageViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()

    private val scaleInAnimationAdapter: ScaleInAnimationAdapter by lazy {
        ScaleInAnimationAdapter(homeHeritageAdapter).apply {
            setDuration(2000)
            setInterpolator(OvershootInterpolator())
            setFirstOnly(false)
        }
    }


    override fun init() {
        binding.user = userViewModel.user.value

        initObserver()

        initAdapter()

        initClickListener()

        setToolbar()
    }

    private fun initObserver() {

        /*
        내가 가입한 모임 목록 groupListAdapter에 submitList 해줘야됨
         */

        heritageViewModel.heritageList.observe(viewLifecycleOwner) { list ->
            // 스크랩 순으로 문화재 추천
            homeHeritageAdapter.submitList(
                list.sortedWith(
                    compareBy({ -it.heritageScrapCnt },
                        { it.heritageSeq })
                )
            )
        }
    }

    private fun initAdapter() = with(binding) {

        // 나의 모임 리스트
        recyclerviewMyGroup.apply {
            adapter = groupListAdapter
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
            /*
            어댑터 연결 필요
             */
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

    // 나의 모임 아이템 클릭시
    override fun onItemClick(position: Int) {

        val action =
            GroupListFragmentDirections.actionGroupListFragmentToGroupInfoFragment(
                groupListAdapter.getItem(
                    position
                )
            )
        findNavController().navigate(action)
    }
}