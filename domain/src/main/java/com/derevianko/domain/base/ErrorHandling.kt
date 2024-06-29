package com.derevianko.domain.base

interface ErrorHandler {

    fun getError(throwable: Throwable): ErrorEntity

    fun getError(statusCode: Int): ErrorEntity
}