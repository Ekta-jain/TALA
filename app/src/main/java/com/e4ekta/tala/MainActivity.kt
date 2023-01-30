package com.e4ekta.tala

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e4ekta.tala.databinding.ActivityMainBinding
import com.e4ekta.tala.models.LoanResponseItem
import com.e4ekta.tala.models.LocalsData
import com.e4ekta.tala.paging.LoaderAdapter
import com.e4ekta.tala.paging.LoanRecordsPagingAdapter
import com.e4ekta.tala.utils.Constants.loadJSONFromAsset
import com.e4ekta.tala.viewmodels.LoanViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var loanViewModel: LoanViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: LoanRecordsPagingAdapter
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val resultsLocals = setupMapWithLocalsJson()

        recyclerView = binding.loanRecordList

        loanViewModel = ViewModelProvider(this).get(LoanViewModel::class.java)

        adapter = LoanRecordsPagingAdapter(resultsLocals, this@MainActivity)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()
        )

        loanViewModel.list.observe(
            this,
            Observer {
                adapter.submitData(lifecycle, it)
            }
        )
    }
    /*this function helps to get map data from local json[which is on assests folder]  */
    private fun setupMapWithLocalsJson(): Map<String, LocalsData> {
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

    override fun onItemClick(item: LoanResponseItem?) {
        // We can pass data via put extra on details activity
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.DATA, item)
        startActivity(intent)
    }
}

interface OnItemClickListener {
    fun onItemClick(item: LoanResponseItem?)
}
