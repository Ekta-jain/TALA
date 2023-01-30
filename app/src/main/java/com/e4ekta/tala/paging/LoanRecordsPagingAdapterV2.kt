package com.e4ekta.tala.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.e4ekta.tala.R
import com.e4ekta.tala.databinding.ItemLoanDueRecordsBinding
import com.e4ekta.tala.databinding.ItemLoanRecordsBinding
import com.e4ekta.tala.models.LoanResponseItem

class LoanRecordsPagingAdapterV2 :
    PagingDataAdapter<LoanResponseItem, RecyclerView.ViewHolder>(COMPARATOR) {

    private val VIEW_TYPE_DUE = 0
    private val VIEW_TYPE_NON_DUE = 1


    class LoanRecordViewHolder(val binding: ItemLoanRecordsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val tvHeader: TextView = binding.tvHeader
        private val tvSubHeader: TextView = binding.tvSubHeader
        val imgHeader: AppCompatImageView = binding.imageHeader

        fun bindData(loanResponseItem: LoanResponseItem) {
           // tvHeader.text = "Loan record"
            loanResponseItem.loan?.let {
                if (it.status == LoanStatus.APPROVED.toString()) {
                    tvHeader.text = binding.root.context.resources.getString(R.string.approved_header);
                    tvSubHeader.text = binding.root.context.resources.getString(R.string.approved_header);
                    imgHeader.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.img_loan_status_approved));
                   // imgHeader.background =  ContextCompat.getDrawable(binding.root.context,R.drawable.img_loan_status_approved )
                } else if (it.status == LoanStatus.PAID.toString()) {
                    tvHeader.text = binding.root.context.resources.getString(R.string.paid_header);
                    tvSubHeader.text = binding.root.context.resources.getString(R.string.paid_sub_header);
                    imgHeader.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.img_loan_status_paidoff));
                  //  imgHeader.background =  ContextCompat.getDrawable(binding.root.context,R.drawable.img_loan_status_paidoff )
                }
            }
        }
    }

    class LoanDueRecordViewHolder(val binding: ItemLoanDueRecordsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvHeader: TextView = binding.tvHeader
        val tvDueAmount: TextView = binding.tvDueAmount

        fun bindData(loanResponseItem: LoanResponseItem) {
            tvHeader.text = binding.root.context.resources.getString(R.string.you_are_on_track);
            tvDueAmount.text = loanResponseItem.loan?.due.toString()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        getItem(position)?.let { item ->
            when (getItemViewType(position)) {
                VIEW_TYPE_DUE -> (holder as LoanDueRecordViewHolder).bindData(item)
                VIEW_TYPE_NON_DUE -> (holder as LoanRecordViewHolder).bindData(item)
                else -> {}
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        Log.i("ViewTypeStatus","="+getItem(position)?.loan?.status)
        return when (  getItem(position)?.loan?.status) {
            "due" -> VIEW_TYPE_DUE

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
        APPROVED, PAID, OFFERED, DUE
    }
}





















