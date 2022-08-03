package com.ssafy.heritage.adpter

import android.content.ClipData
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MemberAdapter(private val listener: OnItemClickListener)
    :RecyclerView.Adapter<MemberAdapter.MemberViewHolder>(){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MemberAdapter.MemberViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MemberAdapter.MemberViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
    inner class MemberViewHolder(private val binding: Item) {
        init {

        }
        fun bind(position: Int)= with(binding){

        }
    }
}