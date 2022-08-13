package com.ssafy.heritage.view.group

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentGroupInfoBinding
import com.ssafy.heritage.viewmodel.GroupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "GroupInfoFragment___"

class GroupInfoFragment : BaseFragment<FragmentGroupInfoBinding>(com.ssafy.heritage.R.layout.fragment_group_info), TabLayout.OnTabSelectedListener{

    private val args by navArgs<GroupInfoFragmentArgs>()
    private val groupViewModel by activityViewModels<GroupViewModel>()

    private lateinit var detailFragment : GroupDetailFragment
    private lateinit var chatFragment: GroupChatFragment
    private lateinit var calenderFragment: GroupCalenderFragment
    private lateinit var mapFragment: GroupMapFragment

    override fun init() {
        CoroutineScope(Dispatchers.Main).launch {
//            groupViewModel.getGroupList()
            groupViewModel.add(args.groupInfo)
           val s= groupViewModel.selectGroupMembers(ApplicationClass.sharedPreferencesUtil.getUser(),args.groupInfo.groupSeq)
            groupViewModel.getChatList(args.groupInfo.groupSeq)
            Log.d(TAG, "init CoroutineScope: $s")
            initAdapter()
            initClickListener()
        }
    }

    private fun initClickListener() = with(binding) {
//        btnBack.setOnClickListener{
//            findNavController().popBackStack()
//        }
    }
    private fun initAdapter() {
        binding.apply {
//
            // 각 탭에 들어갈 프래그먼트 객체화
            detailFragment = GroupDetailFragment()
            chatFragment = GroupChatFragment()
            calenderFragment = GroupCalenderFragment()
            mapFragment = GroupMapFragment()
//
//
//            childFragmentManager.beginTransaction()
////                .replace(R.id.vewtab, detailFragment).commit()
//
//          //  viewpagerTab.addView(LayoutInflater.from(requireContext()).inflate(R.layout.fragment_group_detail,viewpagerTab,false))
//
//            val pages = FragmentPagerItems(requireContext())
//            pages.add(FragmentPagerItem.of("정보", GroupDetailFragment::class.java))
//            pages.add(FragmentPagerItem.of("채팅", GroupChatFragment()::class.java))
//            pages.add(FragmentPagerItem.of("일정", GroupCalenderFragment()::class.java))
//            pages.add(FragmentPagerItem.of("지도", GroupMapFragment()::class.java))

            val adapter = FragmentPagerItemAdapter(
                childFragmentManager, FragmentPagerItems.with(requireContext())
                    .add("정보", GroupDetailFragment::class.java)
                    .add("채팅", GroupChatFragment()::class.java)
                    .add("일정", GroupCalenderFragment()::class.java)
                    .add("지도", GroupMapFragment()::class.java)
                    .create())
            viewpager.adapter = adapter
            viewpagertab.setViewPager(viewpager)

        }
    }



    private fun replaceView(tab: Fragment) {
        // 탭한 화면 변경
        var selectedFragment: Fragment?=null
        selectedFragment = tab
        selectedFragment?.let {
            childFragmentManager?.beginTransaction()?.replace(R.id.viewpager, it)?.commit()
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when(tab!!.position) {
            0 -> replaceView(detailFragment)
            1 -> replaceView(chatFragment)
            2 -> replaceView(calenderFragment)
            3 -> replaceView(mapFragment)
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}
}


