package com.review.interactivestandard.common.data.dto

import com.review.interactivestandard.common.domain.entity.EntityError
import com.review.interactivestandard.common.domain.entity.EntityResult

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: String? = null) :
        ResultWrapper<Nothing>()

    object NetworkError : ResultWrapper<Nothing>()
}

fun <T, I> ResultWrapper<T>.toEntityResult(
    transform: ((acc: T) -> I)
): EntityResult<I> {
    return when (this) {
        is ResultWrapper.Success -> EntityResult.Success(transform.invoke(value))
        is ResultWrapper.GenericError -> EntityResult.Error(
            EntityResult.ErrorType.GenericError(
                this.code,
                this.error?.let { EntityError(this.error) }
            )
        )

        is ResultWrapper.NetworkError -> EntityResult.Error(EntityResult.ErrorType.NetworkError)
    }
}