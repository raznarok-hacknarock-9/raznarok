package com.example.raznarokmobileapp.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val firstName: String,
    val email: String,
    val profilePictureFilename: String = "default_profile.png",
    val descriptionAsHost: String = "",
    val commentsAsHost: List<Comment> = emptyList(),
    val commentsAsVisitor: List<Comment> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val availabilities: List<Availability> = emptyList(),
    val chatsAsHost: List<Chat> = emptyList(),
    val chatsAsVisitor: List<Chat> = emptyList()
)
