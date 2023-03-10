package com.e4ekta.tala.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoanResponseItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("loan")
    val loan: Loan?,
    @SerializedName("locale")
    val locale: String,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("username")
    val username: String
) : Serializable
