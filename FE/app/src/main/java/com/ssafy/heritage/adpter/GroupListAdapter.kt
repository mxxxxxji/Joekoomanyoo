package com.ssafy.heritage.adpter

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.response.GroupListResponse
import com.ssafy.heritage.databinding.ItemGroupBinding

class GroupListAdapter(
    private val listener: AdapterView.OnItemClickListener,
    private val checkListener: View.OnClickListener
) {

//    private var groupList = mutableListOf<GroupListResponse>()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupListViewHolder {
//    }
//
//    override fun onBindViewHolder(holder: GroupListViewHolder, position: Int) {
//    }
//
//    override fun getItemCount(): Int {
//    }
//
//
//    inner class GroupListViewHolder(private val binding: ItemGroupBinding) {
//        init {
//
//        }
//
//        fun bind(position: Int) {
//
//        }
//    }
}