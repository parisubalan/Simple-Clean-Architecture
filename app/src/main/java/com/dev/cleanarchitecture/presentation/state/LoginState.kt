package com.dev.cleanarchitecture.presentation.state

import com.dev.cleanarchitecture.domain.model.response.LoginResponse

data class LoginState(
    val isLoading: Boolean = false,
    val data : LoginResponse? = null,
    val error: String = ""
) {
}