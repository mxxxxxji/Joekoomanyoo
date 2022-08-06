package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.dto.Schedule
import com.ssafy.heritage.databinding.ItemMyScheduleBinding

class ScheduleListAdapter : ListAdapter<Schedule, ScheduleListAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemMyScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Schedule) = with(binding) {
            schedule = data

            btnEdit.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMyScheduleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Schedule>() {
        override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
            return oldItem == newItem
        }
    }
}