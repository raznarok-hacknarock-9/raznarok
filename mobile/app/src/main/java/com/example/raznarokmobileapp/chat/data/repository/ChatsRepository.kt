package com.example.raznarokmobileapp.chat.data.repository

import com.example.raznarokmobileapp.core.data.api.KtorApi
import com.example.raznarokmobileapp.core.domain.model.Chat
import com.example.raznarokmobileapp.core.domain.model.ChatId
import com.example.raznarokmobileapp.core.domain.model.ChatMessage

class ChatsRepository(
    private val api: KtorApi
) {

    suspend fun getChats(userId: Int): List<Chat> {
        return api.getChats(userId)
    }

    suspend fun getChatsWithHost(hostId: Int): List<Chat> {
        return api.getChatsWithHost(hostId)
    }

    suspend fun getChat(chatId: Int): Chat {
        return api.getChat(chatId)
    }

    suspend fun sendMessage(chatId: Int, userId: Int, message: String): ChatMessage {
        return api.sendMessage(chatId, userId, message)
    }

    suspend fun sendCostMessage(chatId: Int, hostId: Int, cost: String): ChatMessage {
        return api.sendCostMessage(chatId, hostId, cost)
    }

    suspend fun startChatWithHost(userId: Int, hostId: Int): ChatId {
        return api.startChatWithHost(userId, hostId)
    }

    suspend fun denyMessage(chatMessageId: Int) {
        api.denyMessage(chatMessageId)
    }

    suspend fun confirmMessage(chatMessageId: Int) {
        api.confirmMessage(chatMessageId)
    }

    suspend fun sendMeetingMessage(chatId: Int, hostId: Int): ChatMessage {
        return api.sendMeetingMessage(chatId, hostId)
    }

    suspend fun addComment(chatId: Int, userId: Int, content: String, rating: Int) {
        api.addComment(chatId, userId, content, rating)
    }

}