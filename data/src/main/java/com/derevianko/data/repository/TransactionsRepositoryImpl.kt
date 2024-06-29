package com.derevianko.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.derevianko.data.local.datasource.TransactionLocalDataSource
import com.derevianko.data.mappers.TransactionsMapper
import com.derevianko.data.utils.TRANSACTION_PAGE_SIZE
import com.derevianko.data.utils.TRANSACTION_PREFETCH_DISTANCE
import com.derevianko.domain.model.Transaction
import com.derevianko.domain.repository.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val localDataSource: TransactionLocalDataSource
): TransactionsRepository {

    override fun getAll(): Flow<PagingData<Transaction>> = Pager(
        PagingConfig(
            pageSize = TRANSACTION_PAGE_SIZE,
            prefetchDistance = TRANSACTION_PREFETCH_DISTANCE
        )
    ) {
        localDataSource.getAll()
    }.flow.map { pagingData ->
        pagingData.map { TransactionsMapper.FromDbToDomain(it) }
    }

    override fun addTransaction(transaction: Transaction) {
        val transactionDbEntity = TransactionsMapper.FromDomainToDb(transaction)
        localDataSource.save(transactionDbEntity)
    }
}