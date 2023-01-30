package com.e4ekta.tala.models

import com.google.gson.annotations.SerializedName

data class LocalsData(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("loanLimit")
    val loanLimit: Int,
    @SerializedName("timezone")
    val timezone: Int
)
