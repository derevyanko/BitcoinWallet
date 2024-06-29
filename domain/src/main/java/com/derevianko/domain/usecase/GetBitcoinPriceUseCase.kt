package com.derevianko.domain.usecase

import com.derevianko.domain.base.ErrorEntity
import com.derevianko.domain.model.CurrentPrice
import com.derevianko.domain.repository.CoindeskRepository
import com.derevianko.domain.util.extensions.MINUTES_TO_GET_BITCOIN_PRICE
import com.derevianko.domain.util.extensions.timeDifferenceInMinutes
import com.derevianko.domain.base.State
import javax.inject.Inject

class GetBitcoinPriceUseCase @Inject constructor(
    private val coindeskRepository: CoindeskRepository
) {

    suspend operator fun invoke(bitcoinPriceState: State<CurrentPrice>): State<CurrentPrice> {
        val bitcoinPrice = if (bitcoinPriceState is State.Success) bitcoinPriceState else return coindeskRepository.getBitcoinPrice()

        val updatedTime = bitcoinPrice.data.time
        val timeDiffInMinutes = updatedTime.timeDifferenceInMinutes() ?: return State.Failure(error = ErrorEntity.Unknown)
        return if (timeDiffInMinutes > MINUTES_TO_GET_BITCOIN_PRICE) {
            coindeskRepository.getBitcoinPrice()
        } else {
            bitcoinPriceState
        }
    }
}