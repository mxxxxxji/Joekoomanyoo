package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.remote.response.MyGroupResponse
import com.ssafy.heritage.databinding.ItemSharedMyGroupBinding

class SharedMyGroupListAdapter() :
    RecyclerView.Adapter<SharedMyGroupListAdapter.SharedMyGroupListViewHolder>() {

    private var myGroupList = mutableListOf<MyGroupResponse>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SharedMyGroupListAdapter.SharedMyGroupListViewHolder {
        val binding =
            ItemSharedMyGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SharedMyGroupListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SharedMyGroupListViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return myGroupList.size
    }

    fun submitList(list: MutableList<MyGroupResponse>) {
        myGroupList = list
        notifyDataSetChanged()
    }

    fun getItem(position: Int): MyGroupResponse {
        return myGroupList[position]
    }

    inner class SharedMyGroupListViewHolder(private val binding: ItemSharedMyGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(position: Int) = with(binding) {
                val item = myGroupList[position]
//                ivGroupImg.text = item.attachSeq
                tvGroupStartDate.text = item.groupStartDate.toString()
                tvGroupEndDate.text = item.groupEndDate.toString()
                tvGroupName.text = item.groupName
            }
        }

}
