package com.example.raznarokmobileapp.chat.presentation.chat_list

sealed interface ChatListScreenEvent {

    data class GoToChat(val chatId: Int) : ChatListScreenEvent
}