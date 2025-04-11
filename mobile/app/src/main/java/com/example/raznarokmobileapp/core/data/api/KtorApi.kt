package com.example.raznarokmobileapp.core.data.api

import android.util.Log
import com.example.raznarokmobileapp.core.domain.model.SimpleUser
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class KtorApi(
    private val client: HttpClient
) {

    suspend fun getUsers(): List<SimpleUser> {
        val result = client.get("${API_BASE_URL}/users")
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

    companion object {
        private const val API_BASE_URL = "http://192.168.1.17:3000/api"
    }

}