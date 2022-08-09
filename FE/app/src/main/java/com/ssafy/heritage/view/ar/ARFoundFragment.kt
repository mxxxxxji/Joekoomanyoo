package com.ssafy.heritage.view.ar

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.BookListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentARFoundBinding
import com.ssafy.heritage.viewmodel.ARViewModel
import com.ssafy.heritage.viewmodel.UserViewModel

private const val TAG = "ARFoundFragment___"

class ARFoundFragment : BaseFragment<FragmentARFoundBinding>(R.layout.fragment_a_r_found) {
    private val bookListAdapter: BookListAdapter by lazy { BookListAdapter() }

    private val userViewModel by activityViewModels<UserViewModel>()
    private val arViewModel by activityViewModels<ARViewModel>()

    override fun init() {
        arViewModel.getAllStamp()

        initAdapter()

        initObserver()
    }

    private fun initAdapter() = with(binding) {
        recyclerView.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 3)
            adapter = bookListAdapter
        }
    }

    private fun initObserver() {
        arViewModel.stampList.observe(viewLifecycleOwner) {

            // 내가 찾은 것들 필터링
            userViewModel.myStampList.value?.forEach { myStamp ->
                it.find { it.stampSeq == myStamp.stampSeq }?.found = 'Y'
            }
            bookListAdapter.submitList(it)
        }
        userViewModel.myStampList.observe(viewLifecycleOwner) {

        }
    }
}