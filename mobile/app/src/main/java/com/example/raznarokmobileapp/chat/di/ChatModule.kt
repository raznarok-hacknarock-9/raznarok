package com.example.raznarokmobileapp.chat.di

import com.example.raznarokmobileapp.chat.data.repository.ChatsRepository
import com.example.raznarokmobileapp.chat.presentation.chat.ChatViewModel
import com.example.raznarokmobileapp.chat.presentation.chat_list.ChatListViewModel
import com.example.raznarokmobileapp.guest.presentation.host_profile.HostProfileViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val chatModule = module {
    viewModelOf(::ChatListViewModel)
    singleOf(::ChatsRepository)
    viewModel { (chatId: Int, userId: Int) -> ChatViewModel(chatId, userId, chatsRepository = get()) }
}