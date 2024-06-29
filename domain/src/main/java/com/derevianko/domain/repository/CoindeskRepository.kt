package com.derevianko.domain.repository

import com.derevianko.domain.model.CurrentPrice
import com.derevianko.domain.base.State

interface CoindeskRepository {

    suspend fun getBitcoinPrice(): State<CurrentPrice>
}