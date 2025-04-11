package com.example.raznarokmobileapp.core.data.repository

import com.example.raznarokmobileapp.core.data.api.KtorApi
import com.example.raznarokmobileapp.core.domain.model.SimpleUser

class UsersRepository(
    private val api: KtorApi
) {

    suspend fun getUsers(): List<SimpleUser> {
        return api.getUsers()
    }

    suspend fun createUser() {
        api.createUser()
    }

}