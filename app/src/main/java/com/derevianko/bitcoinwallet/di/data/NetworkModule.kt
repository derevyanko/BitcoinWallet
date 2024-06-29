package com.derevianko.bitcoinwallet.di.data

import com.derevianko.data.remote.api.CoindeskApi
import com.derevianko.data.remote.datasource.coindesk.CoindeskRemoteDataSource
import com.derevianko.data.remote.datasource.coindesk.CoindeskRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesCoindeskRemoteDataSource(
        api: CoindeskApi,
    ): CoindeskRemoteDataSource = CoindeskRemoteDataSourceImpl(
        api = api
    )
}