package com.derevianko.domain.base

open class State<out T> {

    data class Success<T>(val data: T) : State<T>()

    data class Failure<T>(val error: ErrorEntity) : State<T>()

    object Loading : State<Nothing>()

    object Idle : State<Nothing>()
}