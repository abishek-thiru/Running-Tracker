package com.abi.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abi.auth.domain.AuthRepository
import com.abi.auth.domain.UserDataValidator
import com.abi.auth.presentation.R
import com.abi.core.domain.util.DataError
import com.abi.core.domain.util.Result
import com.abi.core.presentation.ui.UiText
import com.abi.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> login()
            is LoginAction.OnMailIdChanged -> {
                val isEmailValid = userDataValidator.isValidEmail(action.email)
                state = state.copy(
                    email = action.email,
                    canLogin = isEmailValid && state.password.isNotEmpty()
                )
            }

            is LoginAction.OnPasswordChanged -> {
                val isEmailValid = userDataValidator.isValidEmail(state.email)
                state = state.copy(
                    password = action.password,
                    canLogin = isEmailValid && state.password.isNotEmpty()
                )
            }
            LoginAction.OnTogglePasswordVisibility -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoggingIn = true)
            val result = authRepository.login(
                email = state.email.trim(),
                password = state.password
            )
            state = state.copy(isLoggingIn = false)

            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.UNAUTHORIZED) {
                        eventChannel.send(LoginEvent.Error(
                            error = UiText.StringResource(R.string.error_email_password_incorrect)
                        ))
                    } else {
                        eventChannel.send(LoginEvent.Error(
                            error = result.error.asUiText()
                        ))
                    }
                }
                is Result.Success -> {
                    eventChannel.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }

}