package com.derevianko.domain.base

sealed class ErrorEntity {

    object Network : ErrorEntity()

    object NotFound : ErrorEntity()

    object AccessDenied : ErrorEntity()

    object ServiceUnavailable : ErrorEntity()

    object BadRequest : ErrorEntity()

    object Unknown : ErrorEntity()
}