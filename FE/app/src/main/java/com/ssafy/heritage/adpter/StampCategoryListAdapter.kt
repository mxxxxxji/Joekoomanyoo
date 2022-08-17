package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.dto.StampCategory
import com.ssafy.heritage.databinding.ItemBookBinding
import com.ssafy.heritage.listener.CategoryListClickListener

class StampCategoryListAdapter() :
    ListAdapter<StampCategory, StampCategoryListAdapter.ViewHolder>(DiffCallback()) {

    lateinit var categoryListClickListener: CategoryListClickListener

    inner class ViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StampCategory) = with(binding) {
            stamp = data

            itemView.setOnClickListener {
                categoryListClickListener.onClick(bindingAdapterPosition, data)
            }

            // 내가 찾은 것들은 흐릿하게
//            if (data.found == 'Y') {
//                itemView.background = ContextCompat.getDrawable(itemView.context, R.color.black)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<StampCategory>() {
        override fun areItemsTheSame(oldItem: StampCategory, newItem: StampCategory): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: StampCategory, newItem: StampCategory): Boolean {
            return oldItem == newItem
        }
    }
}