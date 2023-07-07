package com.dev.cleanarchitecture.common

import com.google.gson.annotations.SerializedName

data class GetAccessTokenPojo(
    @SerializedName("data")
    val tokenData: TokenData?,

    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String,

    )
data class TokenData(
    @SerializedName("accessToken")
    val accessToken: String,
)