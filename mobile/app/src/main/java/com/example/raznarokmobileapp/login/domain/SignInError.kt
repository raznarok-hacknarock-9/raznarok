package com.example.raznarokmobileapp.login.domain

import com.example.raznarokmobileapp.core.domain.Error

sealed interface SignInError: Error {
    data object NetworkError: SignInError
    data object UnknownError: SignInError
}