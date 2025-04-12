package com.example.raznarokmobileapp.core.data.repository

import com.example.raznarokmobileapp.core.data.api.KtorApi
import com.example.raznarokmobileapp.core.domain.Result
import com.example.raznarokmobileapp.core.domain.model.User
import com.example.raznarokmobileapp.login.domain.SignInError

class UsersRepository(
    private val api: KtorApi
) {

    suspend fun getUsers(): List<User> {
        return api.getUsers()
    }

    suspend fun fetchHosts(
        location: String,
        dateFrom: Long,
        dateTo: Long,
    ): List<User> {
        return api.fetchHosts(
            location,
            dateFrom,
            dateTo
        )
    }

    suspend fun getUserById(
        userId: Int
    ): User {
        return api.getUserById(userId)
    }

    suspend fun createUser() {
        api.createUser()
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<User, SignInError> {
        return api.login(email, password)
    }

}