package com.e4ekta.tala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e4ekta.tala.paging.LoanRecordsPagingAdapter
import com.e4ekta.tala.viewmodels.LoanViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var loanViewModel: LoanViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: LoanRecordsPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.loanRecordList)

        loanViewModel = ViewModelProvider(this).get(LoanViewModel::class.java)

        adapter = LoanRecordsPagingAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        loanViewModel.list.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })
    }
}