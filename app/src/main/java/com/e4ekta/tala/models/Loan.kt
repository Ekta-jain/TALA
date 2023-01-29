package com.e4ekta.tala.models


import com.google.gson.annotations.SerializedName

data class Loan(
    @SerializedName("approved")
    val approved: Int,
    @SerializedName("due")
    val due: Int,
    @SerializedName("dueDate")
    val dueDate: Long,
    @SerializedName("level")
    val level: String,
    @SerializedName("status")
    val status: String
)