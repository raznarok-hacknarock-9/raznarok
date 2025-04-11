package com.example.raznarokmobileapp.core.presentation

import com.example.raznarokmobileapp.core.domain.model.SimpleUser

data class UsersState(
    val users: List<SimpleUser> = emptyList(),
    val storyText: String? = null,
)
