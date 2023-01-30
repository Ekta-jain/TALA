package com.e4ekta.tala

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.e4ekta.tala.databinding.ActivityDetailsBinding
import com.e4ekta.tala.models.LoanResponseItem

class DetailsActivity : AppCompatActivity() {

    companion object {
        val DATA = "DATA"
    }
    lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        val loanResponseItem = intent.extras?.getSerializable(DATA) as LoanResponseItem

        binding.tvDetails.text = loanResponseItem.loan.toString()
    }
}
