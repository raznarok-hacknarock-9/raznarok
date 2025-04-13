package com.example.raznarokmobileapp.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id: Int,
    val content: String,
    val rating: Int,
    val authorId: Int,
    val user: SimpleUser,
)
