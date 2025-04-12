package com.example.raznarokmobileapp.login.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raznarokmobileapp.core.data.repository.UsersRepository
import com.example.raznarokmobileapp.core.domain.model.User
import com.example.raznarokmobileapp.login.domain.SignInError
import kotlinx.coroutines.launch
import com.example.raznarokmobileapp.core.domain.Result

class LoginViewModel(
    private val usersRepository: UsersRepository,
) : ViewModel() {

    var loginState by mutableStateOf(LoginState())

    init {

    }

    fun onLoginScreenEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.EmailChanged -> {
                loginState = loginState.copy(email = event.email)
            }
            is LoginScreenEvent.PasswordChanged -> {
                loginState = loginState.copy(password = event.password)
            }
            is LoginScreenEvent.SignInWithEmailAndPassword -> {
                loginState = loginState.copy(isLoginPending = true)
                signInWithEmailAndPassword()
            }
            is LoginScreenEvent.ResetState -> {
                loginState = loginState.copy(loginError = null)
            }
        }
    }

    private fun signInWithEmailAndPassword() {
        viewModelScope.launch {
            val loginResult = usersRepository.signInWithEmailAndPassword(loginState.email, loginState.password)
            val loginError = when (loginResult) {
                is Result.Error -> {
                    loginResult.error.asUiText()
                }
                is Result.Success -> {
                    null
                }
            }
            loginState = loginState.copy(
                loginError = loginError,
                isLoginPending = false
            )
        }
    }

}