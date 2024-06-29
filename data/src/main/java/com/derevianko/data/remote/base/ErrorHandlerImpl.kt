package com.derevianko.data.remote.base

import com.derevianko.domain.base.ErrorEntity
import com.derevianko.domain.base.ErrorHandler
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class ErrorHandlerImpl: ErrorHandler {

    override fun getError(throwable: Throwable) = when(throwable) {
        is IOException -> ErrorEntity.Network
        is HttpException -> getError(throwable.code())
        else -> ErrorEntity.Unknown
    }

    override fun getError(statusCode: Int) = when(statusCode) {
        HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound
        HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.AccessDenied
        HttpURLConnection.HTTP_CLIENT_TIMEOUT -> ErrorEntity.Network
        HttpURLConnection.HTTP_BAD_REQUEST -> ErrorEntity.BadRequest
        HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable
        else -> ErrorEntity.Unknown
    }
}