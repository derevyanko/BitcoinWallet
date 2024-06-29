package com.derevianko.data.remote.model.dto.price

import com.google.gson.annotations.SerializedName

data class CurrencyDto(
    val code: String,

    val description: String,

    val rate: String,

    @SerializedName("rate_float")
    val rateFloat: Double,

    val symbol: String
)
