package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.databinding.ItemKeywordBinding
import com.ssafy.heritage.listener.RecyclerItemClickListener

class KeywordListAdapter : ListAdapter<String, KeywordListAdapter.ViewHolder>(DiffCallback()) {

    lateinit var recyclerItemClickListener: RecyclerItemClickListener

    inner class ViewHolder(private val binding: ItemKeywordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) = with(binding) {
            name = data

            itemView.setOnClickListener {
                recyclerItemClickListener.onClick(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemKeywordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}