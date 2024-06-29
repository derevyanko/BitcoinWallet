package com.derevianko.data.local.datasource

import com.derevianko.data.local.dao.TransactionDao
import com.derevianko.data.local.entity.TransactionDbEntity
import javax.inject.Inject

class TransactionLocalDataSourceImpl @Inject constructor(
    private val transactionDao: TransactionDao
): TransactionLocalDataSource {

    override fun getAll() = transactionDao.getAll()

    override fun save(transaction: TransactionDbEntity) {
        transactionDao.save(transaction)
    }
}