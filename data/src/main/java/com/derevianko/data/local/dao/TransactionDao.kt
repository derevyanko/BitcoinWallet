package com.derevianko.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.derevianko.data.local.entity.TransactionDbEntity

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions ORDER BY date desc")
    fun getAll(): PagingSource<Int, TransactionDbEntity>

    @Insert(entity = TransactionDbEntity::class)
    fun save(transaction: TransactionDbEntity)
}