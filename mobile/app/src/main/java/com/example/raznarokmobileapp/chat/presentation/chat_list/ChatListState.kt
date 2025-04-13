package com.example.raznarokmobileapp.chat.presentation.chat_list

import com.example.raznarokmobileapp.core.domain.UiText
import com.example.raznarokmobileapp.core.domain.model.Chat

data class ChatListState(
    val message: UiText? = null,
    val chats: List<Chat> = listOf(),
    val isLoadingChats: Boolean = false
)