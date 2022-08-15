package com.ssafy.heritage.view.ar

import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentARPlayBinding

private const val TAG = "ARPlayFragment___"

class ARPlayFragment : BaseFragment<FragmentARPlayBinding>(R.layout.fragment_a_r_play), TabLayout.OnTabSelectedListener  {

    private lateinit var arListFragment: ARListFragment
    private lateinit var arFoundFragment: ARFoundFragment

    override fun init() {
        initAdapter()

        initClickListener()
    }
    private fun initAdapter() = with(binding) {

        arListFragment = ARListFragment()
        arFoundFragment = ARFoundFragment()

        val adapter = FragmentPagerItemAdapter(
            childFragmentManager, FragmentPagerItems.with(requireContext())
                .add("순위 보기", ARListFragment::class.java)
                .add("도감 보기", ARFoundFragment()::class.java)
                .create()
        )
        viewpager.adapter = adapter
        viewpagertab.setViewPager(viewpager)

    }
    private fun initClickListener() = with(binding) {}
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
            0 -> replaceView(arListFragment)
            1 -> replaceView(arFoundFragment)
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}
}