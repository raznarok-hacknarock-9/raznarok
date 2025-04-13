package com.example.raznarokmobileapp.host.presentation.chat_list

import com.example.raznarokmobileapp.core.domain.UiText
import com.example.raznarokmobileapp.core.domain.model.Chat

data class HostChatListState(
    val message: UiText? = null,
    val chats: List<Chat> = listOf(),
    val isLoadingChats: Boolean = false
)