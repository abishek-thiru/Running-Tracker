package com.abi.auth.presentation.register

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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userDataValidator: UserDataValidator,
    private val repository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.OnMailIdChanged -> {
                val isEmailValid = userDataValidator.isValidEmail(action.email)
                state = state.copy(
                    email = action.email,
                    isEmailValid = isEmailValid,
                    canRegister = isEmailValid && state.passwordValidationState.isValidPassword && !state.isRegistering
                )
            }

            is RegisterAction.OnPasswordChanged -> {
                val isPasswordValid = userDataValidator.validatePassword(action.password)
                state = state.copy(
                    password = action.password,
                    passwordValidationState = isPasswordValid,
                    canRegister = state.isEmailValid && isPasswordValid.isValidPassword && !state.isRegistering
                    )
            }

            is RegisterAction.OnRegisterClick -> {
                register()
            }

            is RegisterAction.OnTogglePasswordVisibilityClick -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }

            else -> Unit
        }
    }

    private fun register() {
        viewModelScope.launch {
            state = state.copy(isRegistering = true)
            val result = repository.register(
                email = state.email.trim(),
                password = state.password
            )
            state = state.copy(isRegistering = false)

            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.CONFLICT) {
                        eventChannel.send(RegisterEvent.Error(
                            UiText.StringResource(R.string.error_email_exists)
                        ))
                    } else {
                        eventChannel.send(RegisterEvent.Error(result.error.asUiText()))
                    }
                }
                is Result.Success -> {
                    eventChannel.send(RegisterEvent.RegistrationSuccess)
                }

                else -> {}
            }
        }
    }

}