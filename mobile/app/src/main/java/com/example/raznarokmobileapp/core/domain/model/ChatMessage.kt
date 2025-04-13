package com.example.raznarokmobileapp.core.domain.model

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ChatMessage(
    val id: Int,
    val content: String,
    val isHostMessage: Boolean,
    val timestamp: String,
    val chatId: Int,
    val type: String,
    val status: String,
)
