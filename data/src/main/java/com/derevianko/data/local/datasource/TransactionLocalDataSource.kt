package com.derevianko.data.local.datasource

import androidx.paging.PagingSource
import com.derevianko.data.local.entity.TransactionDbEntity

interface TransactionLocalDataSource {

    fun getAll(): PagingSource<Int, TransactionDbEntity>

    fun save(transaction: TransactionDbEntity)
}