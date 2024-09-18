package com.abi.auth.presentation.login

import com.abi.auth.presentation.register.RegisterAction

sealed interface LoginAction {
    data object OnTogglePasswordVisibility: LoginAction
    data object OnLoginClick: LoginAction
    data object OnRegisterClick: LoginAction
    data class OnMailIdChanged(val email: String): LoginAction
    data class OnPasswordChanged(val password: String): LoginAction
}