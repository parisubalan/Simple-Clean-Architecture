package com.dev.cleanarchitecture.data.remote

import com.dev.cleanarchitecture.data.remote.dto.BasicResponseDto
import com.dev.cleanarchitecture.domain.model.request.LoginReq
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("auth/login")
    suspend fun login(@Body request: LoginReq?): BasicResponseDto?
}