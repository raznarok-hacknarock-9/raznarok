package com.example.raznarokmobileapp.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    val id: Int,
    val name: String,
)
