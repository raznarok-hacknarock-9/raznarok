package com.example.raznarokmobileapp.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SimpleUser(
    val id: Int,
    val firstName: String,
    val profilePictureFilename: String,
)
