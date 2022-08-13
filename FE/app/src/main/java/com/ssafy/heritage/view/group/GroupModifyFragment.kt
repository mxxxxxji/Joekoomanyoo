package com.ssafy.heritage.view.group

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentGroupModifyBinding
import com.ssafy.heritage.viewmodel.GroupViewModel

private const val TAG = "GroupModifyFragment___"

class GroupModifyFragment :
    BaseFragment<FragmentGroupModifyBinding>(R.layout.fragment_group_modify){

   // private val args by navArgs<GroupDetailFragment>()
    private val groupViewModel by viewModels<GroupViewModel>()

    override fun init() {
      //  groupViewModel.selectGroupDetail(grou)
    }
}