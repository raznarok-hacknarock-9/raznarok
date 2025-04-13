package com.example.raznarokmobileapp.chat.presentation.chat

sealed interface ChatScreenEvent {

    data object GoBack: ChatScreenEvent

    data class MessageChanged(val message: String): ChatScreenEvent

    data object SendMessage: ChatScreenEvent

    data class CommentChanged(val comment: String): ChatScreenEvent

    data class SelectRating(val rating: Int): ChatScreenEvent
}