package com.e4ekta.tala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e4ekta.tala.paging.LoaderAdapter
import com.e4ekta.tala.paging.LoanRecordsPagingAdapter
import com.e4ekta.tala.paging.LoanRecordsPagingAdapterV2
import com.e4ekta.tala.viewmodels.LoanViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var loanViewModel: LoanViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: LoanRecordsPagingAdapterV2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.loanRecordList)

        loanViewModel = ViewModelProvider(this).get(LoanViewModel::class.java)

        adapter = LoanRecordsPagingAdapterV2()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()
        )

        loanViewModel.list.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })
    }
}