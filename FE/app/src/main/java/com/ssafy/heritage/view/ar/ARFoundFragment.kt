package com.ssafy.heritage.view.ar

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.StampCategoryListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.StampCategory
import com.ssafy.heritage.databinding.FragmentARFoundBinding
import com.ssafy.heritage.listener.CategoryListClickListener
import com.ssafy.heritage.viewmodel.ARViewModel
import com.ssafy.heritage.viewmodel.UserViewModel

private const val TAG = "ARFoundFragment___"

class ARFoundFragment : BaseFragment<FragmentARFoundBinding>(R.layout.fragment_a_r_found) {

    private val stampCategoryListAdapter: StampCategoryListAdapter by lazy { StampCategoryListAdapter() }

    private val userViewModel by activityViewModels<UserViewModel>()
    private val arViewModel by activityViewModels<ARViewModel>()

    override fun init() {
        arViewModel.getAllStamp()
        arViewModel.getStampCategory()

        initAdapter()

        initObserver()
    }

    private fun initAdapter() = with(binding) {
        recyclerView.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 3)
            adapter = stampCategoryListAdapter

            stampCategoryListAdapter.categoryListClickListener =
                object : CategoryListClickListener {
                    override fun onClick(position: Int, stampCategory: StampCategory) {

                    }
                }
        }
    }

    private fun initObserver() {
        arViewModel.categoryList.observe(viewLifecycleOwner) { list ->
            userViewModel.myStampList.value?.forEach { myStamp ->
                list.find {
                    it.categoryName == myStamp.stampCategory
                }?.add()
            }
            Log.d(TAG, "initObserver: $list")
            stampCategoryListAdapter.submitList(list)
        }

        arViewModel.stampList.observe(viewLifecycleOwner) {

            // 내가 찾은 것들 필터링
//            userViewModel.myStampList.value?.forEach { myStamp ->
//                it.find { it.stampSeq == myStamp.stampSeq }?.found = 'Y'
//            }
//            bookListAdapter.submitList(it)
        }
        userViewModel.myStampList.observe(viewLifecycleOwner) {

        }
    }
}