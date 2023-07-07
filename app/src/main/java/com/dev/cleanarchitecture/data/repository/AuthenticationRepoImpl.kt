package com.dev.cleanarchitecture.data.repository

import com.dev.cleanarchitecture.data.remote.ApiInterface
import com.dev.cleanarchitecture.data.remote.dto.BasicResponseDto
import com.dev.cleanarchitecture.domain.model.request.LoginReq
import com.dev.cleanarchitecture.domain.repository.AuthenticationRepository
import javax.inject.Inject

class AuthenticationRepoImpl @Inject constructor(private val apiInterface: ApiInterface) :
    AuthenticationRepository {

    override suspend fun login(loginReq: LoginReq?): BasicResponseDto? {
        return apiInterface.login(loginReq)
    }

}