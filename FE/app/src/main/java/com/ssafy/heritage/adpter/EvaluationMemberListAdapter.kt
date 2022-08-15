package com.ssafy.heritage.adpter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.dto.Member
import com.ssafy.heritage.databinding.ItemEvaluationListBinding

class EvaluationMemberListAdapter(private val listener: OnItemClickListener):
    RecyclerView.Adapter<EvaluationMemberListAdapter.EvaluationMemberListViewHolder>(){

    val memberList = mutableListOf<Member>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EvaluationMemberListAdapter.EvaluationMemberListViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: EvaluationMemberListAdapter.EvaluationMemberListViewHolder,
        position: Int
    ) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class EvaluationMemberListViewHolder(private val binding: ItemEvaluationListBinding):
        RecyclerView.ViewHolder(binding.root){
        init {

        }
        fun bind(position: Int){

        }
    }
}