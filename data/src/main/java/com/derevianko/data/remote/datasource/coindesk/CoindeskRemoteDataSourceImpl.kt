package com.derevianko.data.remote.datasource.coindesk

import com.derevianko.data.remote.api.CoindeskApi
import com.derevianko.data.remote.base.BaseRemoteDataSource
import com.derevianko.data.remote.base.NetworkState
import com.derevianko.data.remote.model.dto.price.CurrentPriceResponseDto
import javax.inject.Inject

class CoindeskRemoteDataSourceImpl @Inject constructor(
    private val api: CoindeskApi
): BaseRemoteDataSource(), CoindeskRemoteDataSource {

    override suspend fun getBitcoinPrice(): NetworkState<CurrentPriceResponseDto> = request {
        api.getBitcoinCurrentPrice()
    }
}