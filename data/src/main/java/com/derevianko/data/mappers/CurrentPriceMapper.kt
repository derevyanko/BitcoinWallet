package com.derevianko.data.mappers

import com.derevianko.data.remote.model.dto.price.CurrentPriceResponseDto
import com.derevianko.domain.model.CurrentPrice

class CurrentPriceMapper {

    object fromDtoToDomain {

        operator fun invoke(from: CurrentPriceResponseDto) = CurrentPrice(
            time = from.time.updated,
            rate = from.bpi.usd.rate,
            symbol = from.bpi.usd.symbol
        )
    }
}