package com.review.interactivestandard.common.domain.entity

sealed class EntityResult<out T>(
    val data: T? = null,
    val error: ErrorType? = null
) {
    class Success<T>(data: T) : EntityResult<T>(data)
    class Error<T>(error: ErrorType? = null, data: T? = null) : EntityResult<T>(data, error)

    sealed class ErrorType {
        object NetworkError : ErrorType()
        data class GenericError(val code: Int? = null, val error: EntityError? = null) : ErrorType()
    }
}