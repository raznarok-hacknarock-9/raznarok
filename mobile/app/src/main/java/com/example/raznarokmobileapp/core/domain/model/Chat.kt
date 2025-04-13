package com.example.raznarokmobileapp.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Chat(
    val id: Int,
    val host: SimpleUser,
    val visitor: SimpleUser,
    val messages: List<ChatMessage> = emptyList(),
    val cost: Int,
    val isCostConfirmed: Boolean = false,
    val isVisitConfirmed: Boolean = false,
    val hasHostCommented: Boolean = false,
    val hasVisitorCommented: Boolean = false
)
