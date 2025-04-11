package com.example.raznarokmobileapp.core.di

import com.example.raznarokmobileapp.core.data.repository.GeminiRepository
import com.example.raznarokmobileapp.core.data.repository.UsersRepository
import com.example.raznarokmobileapp.core.presentation.UsersViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::UsersViewModel)

    singleOf(::GeminiRepository)
    singleOf(::UsersRepository)
}