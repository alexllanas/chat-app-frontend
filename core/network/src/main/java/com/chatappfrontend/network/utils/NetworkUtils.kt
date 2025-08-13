package com.chatappfrontend.network.utils

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
                ResultWrapper.Failure(
                    response.code(),
                    NetworkResponseParser.getErrorMessage(response.errorBody()?.string())
                )
            }
        } else {
            ResultWrapper.Error()
        }
    } catch (e: Exception) {
        ResultWrapper.Error(e)
    }
}
