package com.example.raznarokmobileapp.chat.presentation.chat

import com.example.raznarokmobileapp.core.domain.UiText
import com.example.raznarokmobileapp.core.domain.model.Chat

data class ChatState(
    val message: UiText? = null,
    val chat: Chat? = null,
    val isLoadingChat: Boolean = false,
    val typedMessage: String = "",
    val comment: String = "",
    val selectedRating: Int = 0,
    val shouldUpdatePoints: Boolean = false,
)