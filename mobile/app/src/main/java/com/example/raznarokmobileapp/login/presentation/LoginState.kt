package com.example.raznarokmobileapp.login.presentation

import com.example.raznarokmobileapp.core.domain.UiText
import com.example.raznarokmobileapp.core.domain.model.User

data class LoginState(
    val email: String = "user5@gmail.com",
    val password: String = "dfssiojw",
    val loginError: UiText? = null,
    val isLoginPending: Boolean = false,
    val loggedInUser: User? = null,
)