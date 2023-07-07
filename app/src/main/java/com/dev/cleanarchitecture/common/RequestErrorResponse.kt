package com.dev.cleanarchitecture.common

import com.google.gson.annotations.SerializedName

data class RequestErrorResponse(

	@field:SerializedName("status_code")
	val statusCode: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("error")
	val error: Any? = null,

	@field:SerializedName("status")
	val status: String? = null
)

