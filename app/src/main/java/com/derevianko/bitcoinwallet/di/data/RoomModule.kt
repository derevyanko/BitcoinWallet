package com.derevianko.bitcoinwallet.di.data

import android.content.Context
import androidx.room.Room
import com.derevianko.data.local.dao.TransactionDao
import com.derevianko.data.local.database.WalletDatabase
import com.derevianko.data.local.datasource.TransactionLocalDataSource
import com.derevianko.data.local.datasource.TransactionLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun providesTransactionLocalDataSource(
        transactionDao: TransactionDao
    ): TransactionLocalDataSource =
        TransactionLocalDataSourceImpl(transactionDao)

    @Provides
    @Singleton
    fun providesWalletRoomDatabase(@ApplicationContext applicationContext: Context): WalletDatabase =
        Room
            .databaseBuilder(applicationContext, WalletDatabase::class.java, "wallet.db")
            .build()

    @Provides
    @Singleton
    fun providesTransactionDao(appDatabase: WalletDatabase) = appDatabase.getTransactionDao()
}