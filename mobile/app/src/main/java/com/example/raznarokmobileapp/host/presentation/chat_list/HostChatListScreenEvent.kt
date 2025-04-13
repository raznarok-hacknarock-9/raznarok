package com.example.raznarokmobileapp.host.presentation.chat_list

sealed interface HostChatListScreenEvent {

    data class GoToChat(val chatId: Int) : HostChatListScreenEvent
}