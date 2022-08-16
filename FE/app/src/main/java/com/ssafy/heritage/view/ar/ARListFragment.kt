package com.ssafy.heritage.view.ar

import android.util.Log
import androidx.fragment.app.activityViewModels
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.StampRankAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.remote.response.StampRankResponse
import com.ssafy.heritage.databinding.FragmentARListBinding
import com.ssafy.heritage.viewmodel.ARViewModel

private const val TAG = "ARListFragment___"

class ARListFragment : BaseFragment<FragmentARListBinding>(R.layout.fragment_a_r_list) {

    private val arViewModel by activityViewModels<ARViewModel>()
    private lateinit var stampRankAdapter: StampRankAdapter
    val userNickname: String? = ApplicationClass.sharedPreferencesUtil.getUserNickName()
    var userRank: Int = 0
    var userStampCnt: Int = 0
    override fun init() {

        arViewModel.selectStampRank()


        initAdapter()
        initObserver()
    }
    private fun initAdapter(){
        stampRankAdapter = StampRankAdapter()
        binding.recyclerviewArRank.adapter = stampRankAdapter
    }

    private fun initObserver() {
        arViewModel.stampRankList.observe(viewLifecycleOwner) {
            var list = mutableListOf<StampRankResponse>()
            list = it.sortedByDescending { it.myStampCnt } as MutableList<StampRankResponse>
            for(i in it){
                if(userNickname == i.userNickname){
                    binding.tvMyRank.text = i.userRank.toString()
                    binding.tvMyRankCountNumber.text = i.myStampCnt.toString()
                }
            }
            Log.d(TAG, list.toString())
            stampRankAdapter.submitList(list)

        }
    }
}