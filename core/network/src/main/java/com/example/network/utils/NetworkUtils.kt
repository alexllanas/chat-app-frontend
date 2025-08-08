package com.example.network.utils

import com.chatappfrontend.common.ResultWrapper
import retrofit2.Response

inline fun <T, R> safeApiCall(
    apiCall: () -> Response<T>,
    onSuccess: (T) -> R
): ResultWrapper<R> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                ResultWrapper.Success(onSuccess(body))
            } else {
                ResultWrapper.Error(
                    response.code(),
                    NetworkResponseParser.getErrorMessage(response.errorBody()?.string())
                )
            }
        } else {
            ResultWrapper.Error(
                response.code(),
                NetworkResponseParser.getErrorMessage(response.errorBody()?.string())
            )
        }
    } catch (e: Exception) {
        ResultWrapper.Exception(e)
    }
}

inline fun <T, R> safeApiCall2(
    apiCall: () -> Response<T>,
    onSuccess: (T) -> R
): ResultWrapper<R> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                ResultWrapper.Success(onSuccess(body))
            } else {
                ResultWrapper.Error(
                    response.code(),
                    NetworkResponseParser.getErrorMessage(response.errorBody()?.string())
                )
            }
        } else {
            ResultWrapper.Error(
                response.code(),
                NetworkResponseParser.getErrorMessage(response.errorBody()?.string())
            )
        }
    } catch (e: Exception) {
        ResultWrapper.Exception(e)
    }
}