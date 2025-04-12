package com.example.raznarokmobileapp.login.di

import com.example.raznarokmobileapp.login.presentation.LoginViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val loginModule = module {
    viewModelOf(::LoginViewModel)
}