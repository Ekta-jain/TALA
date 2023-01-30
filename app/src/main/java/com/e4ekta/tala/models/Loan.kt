package com.e4ekta.tala.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Loan(
    @SerializedName("approved")
    val approvedAmount: Int,
    @SerializedName("due")
    val dueAmount: Int,
    @SerializedName("dueDate")
    val dueDate: Long,
    @SerializedName("level")
    val level: String,
    @SerializedName("status")
    val status: String
) : Serializable
