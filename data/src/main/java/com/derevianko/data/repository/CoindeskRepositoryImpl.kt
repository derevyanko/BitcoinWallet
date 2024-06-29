package com.derevianko.data.repository

import com.derevianko.data.mappers.CurrentPriceMapper
import com.derevianko.data.remote.base.NetworkState
import com.derevianko.data.remote.datasource.coindesk.CoindeskRemoteDataSource
import com.derevianko.domain.model.CurrentPrice
import com.derevianko.domain.repository.CoindeskRepository
import com.derevianko.domain.base.ErrorEntity
import com.derevianko.domain.base.State
import javax.inject.Inject

class CoindeskRepositoryImpl @Inject constructor(
    private val remoteDataSource: CoindeskRemoteDataSource
): CoindeskRepository {

    override suspend fun getBitcoinPrice(): State<CurrentPrice> {
        val state = when (val result = remoteDataSource.getBitcoinPrice()) {
            is NetworkState.Success -> {
                val data = result.data ?: return State.Failure(error = ErrorEntity.Unknown)
                val currentPrice = CurrentPriceMapper.fromDtoToDomain(data)
                State.Success(currentPrice)
            }
            is NetworkState.Failure -> State.Failure(error = ErrorEntity.BadRequest)
            else -> State.Failure(error = ErrorEntity.Unknown)
        }

        return state
    }
}