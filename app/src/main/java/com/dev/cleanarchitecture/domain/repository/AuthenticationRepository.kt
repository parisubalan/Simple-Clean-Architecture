package com.dev.cleanarchitecture.domain.repository

import com.dev.cleanarchitecture.data.remote.dto.BasicResponseDto
import com.dev.cleanarchitecture.domain.model.request.LoginReq

interface AuthenticationRepository {

    suspend fun login(loginReq: LoginReq?) : BasicResponseDto?
}