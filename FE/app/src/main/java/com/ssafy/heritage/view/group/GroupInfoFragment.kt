package com.ssafy.heritage.view.group

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.databinding.FragmentGroupInfoBinding
import com.ssafy.heritage.viewmodel.GroupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "GroupInfoFragment___"

class GroupInfoFragment :
    BaseFragment<FragmentGroupInfoBinding>(com.ssafy.heritage.R.layout.fragment_group_info),
    TabLayout.OnTabSelectedListener {

    private val args by navArgs<GroupInfoFragmentArgs>()
    private val groupViewModel by activityViewModels<GroupViewModel>()

    private lateinit var detailFragment: GroupDetailFragment
    private lateinit var chatFragment: GroupChatFragment
    private lateinit var calenderFragment: GroupCalenderFragment
    private lateinit var mapFragment: GroupMapFragment
    private lateinit var groupInfo: GroupListResponse
    override fun init() {

        getGroupData()

    }

    private fun getGroupData() {
        CoroutineScope(Dispatchers.Main).launch {

            groupViewModel.add(args.groupInfo)
            val s = groupViewModel.selectGroupMembers(
                ApplicationClass.sharedPreferencesUtil.getUser(),
                args.groupInfo.groupSeq
            )

            binding.groupVM = groupViewModel

            groupViewModel.getChatList(args.groupInfo.groupSeq)
            groupViewModel.selectGroupDetail(args.groupInfo.groupSeq)
            groupViewModel.selectGroupSchedule(args.groupInfo.groupSeq)

            Log.d(TAG, "init CoroutineScope: $s")

            binding.groupDetailInfo = groupViewModel.detailInfo?.value!!

            initAdapter()

            initClickListener()
        }
        initObserver()
    }
    private fun initObserver() = with(binding) {
        groupViewModel.detailInfo.observe(viewLifecycleOwner) {
            groupDetailInfo = it
            groupInfo = it
        }
    }
    private fun initClickListener() = with(binding) {
        btnBack.setOnClickListener{
            findNavController().popBackStack()
        }
        // 설정
        binding.btnSetting.setOnClickListener{
            val action = GroupInfoFragmentDirections.actionGroupInfoFragmentToGroupModifyFragment(groupInfo)
            findNavController().navigate(action)
        }

        binding.btnChangeImage.setOnClickListener {

        }
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initAdapter() {
        binding.apply {
//
            // 각 탭에 들어갈 프래그먼트 객체화
            detailFragment = GroupDetailFragment()
            chatFragment = GroupChatFragment()
            calenderFragment = GroupCalenderFragment()
            mapFragment = GroupMapFragment()


            val adapter = FragmentPagerItemAdapter(
                childFragmentManager, FragmentPagerItems.with(requireContext())
                    .add("정보", GroupDetailFragment::class.java)
                    .add("채팅", GroupChatFragment()::class.java)
                    .add("일정", GroupCalenderFragment()::class.java)
                    .add("지도", GroupMapFragment()::class.java)
                    .create()
            )
            viewpager.adapter = adapter
            viewpagertab.setViewPager(viewpager)

            val permission = groupViewModel.groupPermission?.value!!
            if (permission == 3 || permission == 0) {
                viewpager.setOnTouchListener { view, motionEvent ->
                    true
                }
            }

            viewpagertab.setOnTabClickListener {
                if (it > 0) {
                    binding.motionlayout.transitionToEnd()
                } else {
                    binding.motionlayout.transitionToStart()
                }
            }
        }
    }


    private fun replaceView(tab: Fragment) {
        // 탭한 화면 변경
        var selectedFragment: Fragment? = null
        selectedFragment = tab
        selectedFragment?.let {
            childFragmentManager?.beginTransaction()?.replace(R.id.viewpager, it)?.commit()
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab!!.position) {
            0 -> replaceView(detailFragment)
            1 -> replaceView(chatFragment)
            2 -> replaceView(calenderFragment)
            3 -> replaceView(mapFragment)
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}
}


