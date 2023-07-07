package com.dev.cleanarchitecture.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.cleanarchitecture.common.BaseResponse
import com.dev.cleanarchitecture.domain.model.request.LoginReq
import com.dev.cleanarchitecture.domain.usecase.AuthenticationUseCase
import com.dev.cleanarchitecture.presentation.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val authenticationUseCase: AuthenticationUseCase) :
    ViewModel() {

    var loginResponse = MutableLiveData<LoginState>()

    fun login(loginReq: LoginReq) {
        viewModelScope.launch {
            authenticationUseCase.invoke(loginReq).collect {
                when (it) {
                    is BaseResponse.Loading -> {
                        loginResponse.postValue(LoginState(isLoading = true))
                    }

                    is BaseResponse.Success -> {
                        loginResponse.postValue(LoginState(data = it.data))
                    }

                    is BaseResponse.Error -> {
                        loginResponse.postValue(
                            LoginState(
                                error = it.error?.message!!
                            )
                        )
                    }

                    is BaseResponse.ApiFailed -> {
                        loginResponse.postValue(
                            LoginState(
                                error = it.apiFailed!!
                            )
                        )
                    }
                }
            }
        }
    }
}