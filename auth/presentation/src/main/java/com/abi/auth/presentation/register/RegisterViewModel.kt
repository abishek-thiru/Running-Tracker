package com.abi.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.abi.auth.domain.UserDataValidator
import kotlinx.coroutines.flow.onEach

class RegisterViewModel(
    private val userDataValidator: UserDataValidator
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

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

            }

            is RegisterAction.OnTogglePasswordVisibilityClick -> {

            }

            else -> Unit
        }
    }

}