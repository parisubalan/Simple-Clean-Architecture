package com.dev.cleanarchitecture.common

sealed class BaseResponse<T>(
    val data: T? = null,
    val error: RequestErrorResponse? = null,
    val apiFailed: String? = null,
    val isLoading: Boolean? = null
) {
    class Success<T>(data: T) : BaseResponse<T>(data)
    class Error<T>(error: RequestErrorResponse?, data: T? = null) : BaseResponse<T>(data, error)
    class ApiFailed<T>(apiFailed: String?,data: T? = null,error: RequestErrorResponse?=null) : BaseResponse<T>(data,error,apiFailed)
    class Loading<T>(isLoading: T? = null) : BaseResponse<T>(isLoading)
}