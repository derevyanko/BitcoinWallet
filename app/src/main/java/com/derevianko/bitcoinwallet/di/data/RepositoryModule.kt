package com.derevianko.bitcoinwallet.di.data

import com.derevianko.data.local.datasource.TransactionLocalDataSource
import com.derevianko.data.remote.datasource.coindesk.CoindeskRemoteDataSource
import com.derevianko.data.repository.CoindeskRepositoryImpl
import com.derevianko.data.repository.TransactionsRepositoryImpl
import com.derevianko.domain.repository.CoindeskRepository
import com.derevianko.domain.repository.TransactionsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesCoindeskRepository(
        remoteDataSource: CoindeskRemoteDataSource,
    ): CoindeskRepository = CoindeskRepositoryImpl(remoteDataSource)

    @Singleton
    @Provides
    fun providesTransactionsRepository(
        localDataSource: TransactionLocalDataSource
    ): TransactionsRepository = TransactionsRepositoryImpl(localDataSource)
}