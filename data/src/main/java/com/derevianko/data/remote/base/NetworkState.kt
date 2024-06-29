package com.derevianko.data.remote.base

open class NetworkState<out T> {

    data class Success<T>(val data: T?) : NetworkState<T>()

    data class Failure<T>(val statusCode: Int, val exception: Throwable? = null) : NetworkState<T>()
}