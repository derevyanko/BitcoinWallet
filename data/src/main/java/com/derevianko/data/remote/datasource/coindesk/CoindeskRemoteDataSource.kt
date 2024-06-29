package com.derevianko.data.remote.datasource.coindesk

import com.derevianko.data.remote.base.NetworkState
import com.derevianko.data.remote.model.dto.price.CurrentPriceResponseDto

interface CoindeskRemoteDataSource {

    suspend fun getBitcoinPrice(): NetworkState<CurrentPriceResponseDto>
}