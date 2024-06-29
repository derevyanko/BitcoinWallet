package com.derevianko.bitcoinwallet.di.data

import com.derevianko.bitcoinwallet.BuildConfig
import com.derevianko.data.remote.api.CoindeskApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun providesCoindeskApiService(retrofit: Retrofit): CoindeskApi =
        retrofit.create(CoindeskApi::class.java)

    @Provides
    @Singleton
    fun providesRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL_COINDESK)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    @Singleton
    fun providesGsonConvertorFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun providesInterceptor() = Interceptor { chain ->
        val originalRequest = chain.request()
        val httpUrl = originalRequest.url.newBuilder().build()

        val request = originalRequest.newBuilder().url(httpUrl).build()
        return@Interceptor chain.proceed(request)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        interceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .build()
}