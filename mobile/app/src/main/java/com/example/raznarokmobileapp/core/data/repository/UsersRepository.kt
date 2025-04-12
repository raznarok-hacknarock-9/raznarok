package com.example.raznarokmobileapp.core.data.repository

import com.example.raznarokmobileapp.core.data.api.KtorApi
import com.example.raznarokmobileapp.core.domain.Result
import com.example.raznarokmobileapp.core.domain.model.SimpleUser
import com.example.raznarokmobileapp.login.domain.SignInError

class UsersRepository(
    private val api: KtorApi
) {

    suspend fun getUsers(): List<SimpleUser> {
        return api.getUsers()
    }

    suspend fun createUser() {
        api.createUser()
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<Unit, SignInError> {
        return api.login(email, password)
    }

}