package com.derevianko.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.derevianko.data.local.dao.TransactionDao
import com.derevianko.data.local.entity.TransactionDbEntity

@Database(
    version = 1,
    entities = [
        TransactionDbEntity::class
    ]
)
abstract class WalletDatabase: RoomDatabase() {

    abstract fun getTransactionDao(): TransactionDao
}