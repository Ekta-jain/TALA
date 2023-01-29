package com.e4ekta.tala.models


import com.e4ekta.tala.models.Loan
import com.google.gson.annotations.SerializedName

data class LoanResponseItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("loan")
    val loan: Loan,
    @SerializedName("locale")
    val locale: String,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("username")
    val username: String
)