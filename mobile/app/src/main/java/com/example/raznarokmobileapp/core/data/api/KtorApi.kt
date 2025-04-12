package com.example.raznarokmobileapp.core.data.api

import android.util.Log
import com.example.raznarokmobileapp.core.domain.Result
import com.example.raznarokmobileapp.core.domain.model.User
import com.example.raznarokmobileapp.login.domain.SignInError
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

    suspend fun getUsers(): List<User> {
        val result = client.get("${API_BASE_URL}/users")
        return result.body()
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