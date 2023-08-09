package com.review.interactivestandard.common.data

import com.review.interactivestandard.common.data.dto.ResultWrapper
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return try {
        val t = apiCall.invoke()
        if (t is Response<*>) {
            if (t.isSuccessful)
                ResultWrapper.Success(t)
            else {
                ResultWrapper.GenericError(
                    t.code(),
                    getErrorMessage(t.errorBody())
                )
            }
        } else
            ResultWrapper.Success(t)
    } catch (throwable: Throwable) {
        Timber.e(throwable, "safeapicall exception")
        when (throwable) {
            is IOException -> ResultWrapper.NetworkError
            is HttpException -> {
                ResultWrapper.GenericError(
                    throwable.code(),
                    getErrorMessage(throwable.response()?.errorBody())
                )
            }

            else -> {
                ResultWrapper.GenericError(null, null)
            }
        }
    }
}

private fun getErrorMessage(body: ResponseBody?): String? {
    return body?.let {
        body.string()
    }
}