package com.example.raznarokmobileapp.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SimpleUser(
    val id: Int,
    val email: String,
)
