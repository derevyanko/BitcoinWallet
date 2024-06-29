package com.derevianko.data.remote.model.dto.price

data class CurrentPriceResponseDto(
    val time: TimeDto,
    val bpi: BpiDto
)