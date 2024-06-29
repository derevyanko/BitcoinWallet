package com.derevianko.data.remote.api

import com.derevianko.data.remote.model.dto.price.CurrentPriceResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface CoindeskApi {

    @GET("v1/bpi/currentprice.json")
    suspend fun getBitcoinCurrentPrice(): Response<CurrentPriceResponseDto>
}