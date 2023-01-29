package com.e4ekta.tala.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.e4ekta.tala.R
import com.e4ekta.tala.databinding.ItemLoanRecordsBinding
import com.e4ekta.tala.models.LoanResponseItem

class LoanRecordsPagingAdapter :
    PagingDataAdapter<LoanResponseItem, LoanRecordsPagingAdapter.LoanRecordViewHolder>(COMPARATOR) {

    class LoanRecordViewHolder(binding: ItemLoanRecordsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvHeader: TextView = binding.tvHeader
        val tvSubHeader: TextView = binding.tvSubHeader
    }

    override fun onBindViewHolder(holder: LoanRecordViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.tvHeader.text = item.username
            if (item.loan.status == LoanStatus.APPROVED.toString()) {
               // holder.tvHeader.text = holder.itemView.context.resources.getString(R.string.approved_header);
              //  holder.tvSubHeader.text = holder.itemView.context.resources.getString(R.string.approved_header);
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanRecordViewHolder {
        return LoanRecordViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_loan_records,
                parent,
                false
            )
        )
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<LoanResponseItem>() {
            override fun areItemsTheSame(
                oldItem: LoanResponseItem,
                newItem: LoanResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: LoanResponseItem,
                newItem: LoanResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


    enum class LoanStatus {
        APPROVED, PAID, OFFERED, DUE
    }
}





















