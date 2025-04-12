package com.example.raznarokmobileapp.guest.di

import com.example.raznarokmobileapp.guest.presentation.home.GuestHomeViewModel
import com.example.raznarokmobileapp.guest.presentation.host_profile.HostProfileScreen
import com.example.raznarokmobileapp.guest.presentation.host_profile.HostProfileViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val guestModule = module {
    singleOf(::GuestHomeViewModel)
    viewModel { (userId: Int) -> HostProfileViewModel(userId, usersRepository = get()) }
}