package com.derevianko.data.remote.model.dto.price

import com.google.gson.annotations.SerializedName

data class BpiDto(
    @SerializedName("USD")
    val usd: CurrencyDto,
)
