package com.example.raznarokmobileapp.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Chat(
    val id: Int,
    val hostId: Int,
    val visitorId: Int,
    val messages: List<ChatMessage> = emptyList(),
    val hasHostConfirmed: Boolean = false,
    val hasVisitorConfirmed: Boolean = false,
    val hasHostCommented: Boolean = false,
    val hasVisitorCommented: Boolean = false
)
