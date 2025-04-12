package com.example.raznarokmobileapp.core.data.api

import android.util.Log
import com.example.raznarokmobileapp.core.domain.Result
import com.example.raznarokmobileapp.core.domain.model.SimpleUser
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
        Log.d("XXX", result.body())
    }

    suspend fun login(email: String, password: String): Result<Unit, SignInError> {
        val json = buildJsonObject {
            put("email", email)
            put("password", password)
        }
        val result = client.post("${API_BASE_URL}/login") {
            contentType(ContentType.Application.Json)
            setBody(json)
        }
        //check
        return Result.Success(Unit)
    }

    companion object {
        private const val API_BASE_URL = "http://172.98.1.172:3000/api"
    }

}