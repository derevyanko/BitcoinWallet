package com.derevianko.bitcoinwallet.di.domain

import com.derevianko.domain.repository.CoindeskRepository
import com.derevianko.domain.repository.TransactionsRepository
import com.derevianko.domain.usecase.AddTransactionUseCase
import com.derevianko.domain.usecase.GetAllTransactionsUseCase
import com.derevianko.domain.usecase.GetBitcoinPriceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun providesGetAllTransactionsUseCase(
        repository: TransactionsRepository
    ): GetAllTransactionsUseCase = GetAllTransactionsUseCase(repository)

    @Provides
    fun providesAddTransactionUseCase(
        repository: TransactionsRepository
    ): AddTransactionUseCase = AddTransactionUseCase(repository)

    @Provides
    fun providesGetBitcoinPriceUseCase(
        repository: CoindeskRepository
    ): GetBitcoinPriceUseCase = GetBitcoinPriceUseCase(repository)

    @Provides
    @Singleton
    fun providesCoroutineContext(): CoroutineContext = Job() + Dispatchers.IO
}