package com.example.raznarokmobileapp.host.di

import com.example.raznarokmobileapp.chat.presentation.chat.ChatViewModel
import com.example.raznarokmobileapp.guest.presentation.host_profile.HostProfileViewModel
import com.example.raznarokmobileapp.host.presentation.chat.HostChatViewModel
import com.example.raznarokmobileapp.host.presentation.chat_list.HostChatListViewModel
import com.example.raznarokmobileapp.host.presentation.profile_edit.HostProfileEditViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val hostModule = module {
    viewModelOf(::HostChatViewModel)
    viewModelOf(::HostChatListViewModel)
    viewModel { (hostId: Int) -> HostProfileEditViewModel(hostId, usersRepository = get()) }
}