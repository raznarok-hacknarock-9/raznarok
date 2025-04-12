package com.example.raznarokmobileapp.login.presentation

import com.example.raznarokmobileapp.R
import com.example.raznarokmobileapp.core.domain.UiText
import com.example.raznarokmobileapp.login.domain.SignInError

fun SignInError.asUiText(): UiText {
    return when (this) {
        is SignInError.NetworkError -> {
            UiText.StringResource(R.string.network_error)
        }
        is SignInError.UnknownError -> {
            UiText.StringResource(R.string.unknown_error)
        }
    }
}