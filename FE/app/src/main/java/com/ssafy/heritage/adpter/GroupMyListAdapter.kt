package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.remote.response.MyGroupResponse
import com.ssafy.heritage.databinding.ItemGroupMyBinding

class GroupMyListAdapter :
    ListAdapter<MyGroupResponse, GroupMyListAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemGroupMyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MyGroupResponse) = with(binding) {
            myGroupResponse = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGroupMyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<MyGroupResponse>() {
        override fun areItemsTheSame(
            oldItem: MyGroupResponse,
            newItem: MyGroupResponse
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem: MyGroupResponse,
            newItem: MyGroupResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
}