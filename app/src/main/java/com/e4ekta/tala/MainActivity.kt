package com.e4ekta.tala

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e4ekta.tala.models.LocalsData
import com.e4ekta.tala.paging.LoaderAdapter
import com.e4ekta.tala.paging.LoanRecordsPagingAdapterV2
import com.e4ekta.tala.utils.Constants.loadJSONFromAsset
import com.e4ekta.tala.viewmodels.LoanViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import kotlin.collections.HashMap


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var loanViewModel: LoanViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: LoanRecordsPagingAdapterV2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resultsLocals = setupMapWithLocalsJson()

        recyclerView = findViewById(R.id.loanRecordList)

        loanViewModel = ViewModelProvider(this).get(LoanViewModel::class.java)

        adapter = LoanRecordsPagingAdapterV2(resultsLocals)

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
    /*this function helps to get map data from local json[which is on assests folder]  */
    private fun setupMapWithLocalsJson(): Map<String,LocalsData> {
        val localsString = loadJSONFromAsset(this, "locals.json")
        val obj = JSONObject(localsString!!)
        val results: HashMap<String, LocalsData> = HashMap()
        val iter: Iterator<String> = obj.keys()
        while (iter.hasNext()) {
            val key = iter.next()
            val gson = Gson()
            val localsData: LocalsData = gson.fromJson(obj.getString(key + ""), LocalsData::class.java)
            results[key] = localsData
        }
        return results
    }
}