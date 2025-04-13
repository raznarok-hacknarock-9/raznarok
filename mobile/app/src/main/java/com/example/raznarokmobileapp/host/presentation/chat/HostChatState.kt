package com.example.raznarokmobileapp.host.presentation.chat

import com.example.raznarokmobileapp.core.domain.UiText
import com.example.raznarokmobileapp.core.domain.model.Chat

data class HostChatState(
    val message: UiText? = null,
    val chat: Chat? = null,
    val isLoadingChat: Boolean = false,
    val typedMessage: String = "",
    val cost: String = "10",
)