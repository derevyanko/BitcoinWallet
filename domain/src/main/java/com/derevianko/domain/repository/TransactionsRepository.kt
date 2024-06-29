package com.derevianko.domain.repository

import androidx.paging.PagingData
import com.derevianko.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionsRepository {

    fun getAll(): Flow<PagingData<Transaction>>

    fun addTransaction(transaction: Transaction)
}