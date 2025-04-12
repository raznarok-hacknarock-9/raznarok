package com.example.raznarokmobileapp.guest.di

import com.example.raznarokmobileapp.guest.presentation.GuestHomeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val guestModule = module {
    singleOf(::GuestHomeViewModel)
}