package com.dev.cleanarchitecture.data.remote.dto

import com.dev.cleanarchitecture.domain.model.response.LoginResponse
import com.google.gson.annotations.SerializedName

data class BasicResponseDto(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("error")
    val error: String? = null,

    @field:SerializedName("status_code")
    val statusCode: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

fun BasicResponseDto.toBasicResponse(): LoginResponse {
    return LoginResponse(message = message, status = status)
}