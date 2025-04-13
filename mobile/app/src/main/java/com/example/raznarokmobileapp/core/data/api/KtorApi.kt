package com.example.raznarokmobileapp.core.data.api

import android.util.Log
import com.example.raznarokmobileapp.core.domain.Result
import com.example.raznarokmobileapp.core.domain.model.Chat
import com.example.raznarokmobileapp.core.domain.model.ChatId
import com.example.raznarokmobileapp.core.domain.model.ChatMessage
import com.example.raznarokmobileapp.core.domain.model.User
import com.example.raznarokmobileapp.guest.presentation.utils.API_BASE_URL
import com.example.raznarokmobileapp.login.domain.SignInError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class KtorApi(
    private val client: HttpClient
) {

    suspend fun getUsers(): List<User> {
        val result = client.get("${API_BASE_URL}/users")
        return result.body()
    }

    suspend fun getChats(userId: Int): List<Chat> {
        val result = client.get("${API_BASE_URL}/chats") {
            url {
                parameters.append("visitorId", userId.toString())
            }
        }
        return result.body()
    }

    suspend fun getChatsWithHost(hostId: Int): List<Chat> {
        val result = client.get("${API_BASE_URL}/chats") {
            url {
                parameters.append("hostId", hostId.toString())
            }
        }
        return result.body()
    }

    suspend fun getChat(chatId: Int): Chat {
        val result = client.get("${API_BASE_URL}/chats/$chatId")
        return result.body()
    }

    suspend fun startChatWithHost(userId: Int, hostId: Int): ChatId {
        val json = buildJsonObject {
            put("visitorId", userId)
            put("hostId", hostId)
        }
        val result = client.post("${API_BASE_URL}/chats") {
            contentType(ContentType.Application.Json)
            setBody(json)
        }
        return result.body()
    }

    suspend fun sendMessage(chatId: Int, userId: Int, message: String): ChatMessage {
        val json = buildJsonObject {
            put("content", message)
            put("userId", userId)
        }
        val result = client.post("${API_BASE_URL}/chats/${chatId}/messages") {
            contentType(ContentType.Application.Json)
            setBody(json)
        }
        return result.body()
    }

    suspend fun sendCostMessage(chatId: Int, hostId: Int, cost: String): ChatMessage {
        val json = buildJsonObject {
            put("content", cost)
            put("userId", hostId)
            put("type", "cost")
        }
        val result = client.post("${API_BASE_URL}/chats/${chatId}/messages") {
            contentType(ContentType.Application.Json)
            setBody(json)
        }
        return result.body()
    }

    suspend fun sendMeetingMessage(chatId: Int, hostId: Int): ChatMessage {
        val json = buildJsonObject {
            put("userId", hostId)
            put("type", "meeting")
        }
        val result = client.post("${API_BASE_URL}/chats/${chatId}/messages") {
            contentType(ContentType.Application.Json)
            setBody(json)
        }
        return result.body()
    }

    suspend fun denyMessage(chatMessageId: Int) {
        val json = buildJsonObject {
            put("status", "canceled")
        }
        val result = client.put("${API_BASE_URL}/chats/messages/${chatMessageId}") {
            contentType(ContentType.Application.Json)
            setBody(json)
        }
    }

    suspend fun confirmMessage(chatMessageId: Int) {
        val json = buildJsonObject {
            put("status", "confirmed")
        }
        val result = client.put("${API_BASE_URL}/chats/messages/${chatMessageId}") {
            contentType(ContentType.Application.Json)
            setBody(json)
        }
    }

    suspend fun addComment(chatId: Int, userId: Int, content: String, rating: Int) {
        val json = buildJsonObject {
            put("userId", userId)
            put("content", content)
            put("rating", rating)
        }
        client.post("${API_BASE_URL}/chats/${chatId}/comment") {
            contentType(ContentType.Application.Json)
            setBody(json)
        }
    }

    suspend fun fetchHosts(
        location: String,
        dateFrom: Long,
        dateTo: Long,
    ): List<User> {
        val result = client.get("${API_BASE_URL}/users") {
            url {
                parameters.append("location", location)
                parameters.append("dateFrom", dateFrom.toString())
                parameters.append("dateTo", dateTo.toString())
            }
        }
        return result.body()
    }

    suspend fun getUserById(
        userId: Int
    ): User {
        val result = client.get("${API_BASE_URL}/users/$userId")
        return result.body()
    }

    suspend fun createUser() {
        val json = buildJsonObject {
            put("email", "adam@example.com")
        }
        val result = client.post("${API_BASE_URL}/users") {
            contentType(ContentType.Application.Json)
            setBody(json)
        }
    }

    suspend fun login(email: String, password: String): Result<User, SignInError> {
        val json = buildJsonObject {
            put("email", email)
            put("password", password)
        }
        val result = client.post("${API_BASE_URL}/users/login") {
            contentType(ContentType.Application.Json)
            setBody(json)
        }
        return Result.Success(result.body())
    }

    companion object {
        private const val API_BASE_URL = "http://172.98.1.172:3000/api"
    }

}