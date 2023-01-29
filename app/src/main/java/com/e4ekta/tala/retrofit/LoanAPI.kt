package com.e4ekta.tala.retrofit

import com.e4ekta.tala.models.LoanResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface LoanAPI {

    @GET("/records")
    suspend fun getRecords(@Query("pageno") pageno: Int, @Query("pagesize") pagesize: Int): List<LoanResponseItem>
}