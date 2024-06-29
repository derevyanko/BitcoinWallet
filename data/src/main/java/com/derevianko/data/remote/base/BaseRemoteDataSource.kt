package com.derevianko.data.remote.base

import retrofit2.Response
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRemoteDataSource {

    suspend fun <T> request(call: suspend () -> Response<T>): NetworkState<T> = try {
        val response = call.invoke()

        if (response.isSuccessful) {
            NetworkState.Success(response.body())
        } else {
            NetworkState.Failure(statusCode = response.code())
        }
    } catch (exception: Throwable) {
        when (exception) {
            is SocketTimeoutException -> NetworkState.Failure(
                statusCode = HttpURLConnection.HTTP_CLIENT_TIMEOUT,
                exception = exception
            )
            is UnknownHostException -> NetworkState.Failure(
                statusCode = HttpURLConnection.HTTP_BAD_REQUEST,
                exception = exception
            )
            else -> NetworkState.Failure(
                statusCode = HttpURLConnection.HTTP_INTERNAL_ERROR,
                exception = exception
            )
        }
    }
}