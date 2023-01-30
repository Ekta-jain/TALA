package com.e4ekta.tala.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.e4ekta.tala.OnItemClickListener
import com.e4ekta.tala.R
import com.e4ekta.tala.databinding.ItemLoanDueRecordsBinding
import com.e4ekta.tala.databinding.ItemLoanRecordsBinding
import com.e4ekta.tala.models.LoanResponseItem
import com.e4ekta.tala.models.LocalsData

class LoanRecordsPagingAdapter(val result: Map<String, LocalsData>, val listener: OnItemClickListener) :
    PagingDataAdapter<LoanResponseItem, RecyclerView.ViewHolder>(COMPARATOR) {

    private val VIEW_TYPE_DUE = 0
    private val VIEW_TYPE_NON_DUE = 1

    inner class LoanRecordViewHolder(val binding: ItemLoanRecordsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val tvHeader: TextView = binding.tvHeader
        private val tvSubHeader: TextView = binding.tvSubHeader
        val imgHeader: AppCompatImageView = binding.imageHeader

        fun bindData(loanResponseItem: LoanResponseItem) {
            val localsData = result[loanResponseItem.locale] as LocalsData
            loanResponseItem.loan?.let {
                if (it.status == LoanStatus.approved.toString()) {
                    tvHeader.text = binding.root.context.resources.getString(R.string.approved_header)
                    tvSubHeader.text = String.format(
                        binding.root.context.resources.getString(R.string.approved_sub_header),
                        localsData.currency,
                        it.approvedAmount
                    )
                    imgHeader.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.img_loan_status_approved
                    )
                } else if (it.status == LoanStatus.paid.toString()) {
                    tvHeader.text = binding.root.context.resources.getString(R.string.paid_header)
                    tvSubHeader.text = binding.root.context.resources.getString(R.string.paid_sub_header)
                    imgHeader.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.img_loan_status_paidoff
                    )
                }
            }

            itemView.setOnClickListener {
                listener.onItemClick(loanResponseItem)
            }
        }
    }

    inner class LoanDueRecordViewHolder(val binding: ItemLoanDueRecordsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvHeader: TextView = binding.tvHeader
        val tvDueAmount: TextView = binding.tvDueAmount

        fun bindData(loanResponseItem: LoanResponseItem) {
            tvHeader.text = binding.root.context.resources.getString(R.string.you_are_on_track)
            val localsData = result[loanResponseItem.locale] as LocalsData
            tvDueAmount.text = String.format(
                binding.root.context.resources.getString(R.string.due_amount),
                localsData.currency, loanResponseItem.loan?.dueAmount
            )
            itemView.setOnClickListener {
                listener.onItemClick(loanResponseItem)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        item?.let { it ->
            when (getItemViewType(position)) {
                VIEW_TYPE_DUE -> (holder as LoanDueRecordViewHolder).bindData(it)
                VIEW_TYPE_NON_DUE -> (holder as LoanRecordViewHolder).bindData(it)
                else -> {}
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)?.loan?.status) {
            LoanStatus.due.toString() -> VIEW_TYPE_DUE
            else -> VIEW_TYPE_NON_DUE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DUE -> LoanDueRecordViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_loan_due_records,
                    parent,
                    false
                )
            )
            else -> LoanRecordViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_loan_records,
                    parent,
                    false
                )
            )
        }
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
        approved, paid, due
    }
}
