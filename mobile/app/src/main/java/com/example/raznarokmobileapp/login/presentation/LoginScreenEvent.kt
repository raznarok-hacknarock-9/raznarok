package com.example.raznarokmobileapp.login.presentation

sealed interface LoginScreenEvent {

    data class EmailChanged(val email: String): LoginScreenEvent

    data class PasswordChanged(val password: String): LoginScreenEvent

    data object SignInWithEmailAndPassword: LoginScreenEvent

    data object ResetState: LoginScreenEvent
}