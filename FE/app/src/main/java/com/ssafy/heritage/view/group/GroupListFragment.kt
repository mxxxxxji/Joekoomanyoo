package com.ssafy.heritage.view.group

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.GroupListAdapter
import com.ssafy.heritage.adpter.OnItemClickListener
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.databinding.FragmentGroupListBinding
import com.ssafy.heritage.view.dialog.SelectCategoryDialog
import com.ssafy.heritage.viewmodel.GroupViewModel

private const val TAG = "GroupListFragment___"

class GroupListFragment :
    BaseFragment<FragmentGroupListBinding>(R.layout.fragment_group_list), OnItemClickListener {

    private lateinit var groupListAdapter: GroupListAdapter
    private val groupViewModel by viewModels<GroupViewModel>()
    val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()

    override fun init() {

        groupViewModel.getGroupList()
        initAdapter()
        initObserver()
        initClickListener()
    }

    private fun initAdapter() {
        groupListAdapter = GroupListAdapter(this)
        binding.recyclerviewGroupList.adapter = groupListAdapter
    }

    private fun initObserver() {
        groupViewModel.groupList.observe(viewLifecycleOwner) {
            // 진행
            var list = mutableListOf<GroupListResponse>()
            for (i in it) {
                if (i.groupStatus == 'R') {
                    list.add(i)
                }
            }
            groupListAdapter.submitList(list)
        }
    }

    private fun initClickListener() {
        binding.apply {
            fabCreateGroup.setOnClickListener {
                findNavController().navigate(R.id.action_groupListFragment_to_groupCreateFragment)
            }
            btnMyGroup.setOnClickListener {
                findNavController().navigate(R.id.action_groupListFragment_to_myGroupListFragment)
            }
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnSort.setOnClickListener {
                val selectCategoryDialog = SelectCategoryDialog()
                selectCategoryDialog.show(childFragmentManager, "selectCategoryDialog")
                selectCategoryDialog.adapter = groupListAdapter
                selectCategoryDialog.list =
                    groupViewModel.groupList.value!!.filter { it.groupStatus == 'R' } as MutableList<GroupListResponse>
            }
        }
    }

    override fun onItemClick(position: Int) {
        Log.d(TAG, "onItemClick: ${position}")
        val action = GroupListFragmentDirections.actionGroupListFragmentToGroupInfoFragment(
            groupListAdapter.getItem(position)
        )
        findNavController().navigate(action)
    }

}