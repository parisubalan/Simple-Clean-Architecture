package com.dev.cleanarchitecture.domain.usecase

import com.dev.cleanarchitecture.common.BaseResponse
import com.dev.cleanarchitecture.common.RequestErrorResponse
import com.dev.cleanarchitecture.data.remote.dto.toBasicResponse
import com.dev.cleanarchitecture.domain.model.request.LoginReq
import com.dev.cleanarchitecture.domain.model.response.LoginResponse
import com.dev.cleanarchitecture.domain.repository.AuthenticationRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {

    operator fun invoke(loginReq: LoginReq?): Flow<BaseResponse<LoginResponse>> = flow {
        try {
            emit(BaseResponse.Loading())
            val value = authenticationRepository.login(loginReq)?.toBasicResponse()
            value?.let {
                emit(BaseResponse.Success(it))
            }
        } catch (e: HttpException) {
            val errorResponse =
                Gson().fromJson(e.response()?.errorBody()?.string(), RequestErrorResponse::class.java)
            emit(BaseResponse.Error(errorResponse))
        } catch (e: IOException) {
            emit(BaseResponse.ApiFailed(e.message?:"Please check your internet connection"))
        }
    }

}