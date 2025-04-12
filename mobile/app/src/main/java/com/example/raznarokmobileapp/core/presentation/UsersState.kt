package com.example.raznarokmobileapp.core.presentation

import com.example.raznarokmobileapp.core.domain.model.User

data class UsersState(
    val users: List<User> = emptyList(),
    val storyText: String? = null,
)
