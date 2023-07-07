package com.dev.cleanarchitecture.domain.model.request

import com.google.gson.annotations.SerializedName

data class LoginReq(

	@field:SerializedName("username")
	val username: String? = null
)
