package com.example.raznarokmobileapp.chat.presentation.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raznarokmobileapp.chat.data.repository.ChatsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ChatViewModel(
    private val chatId: Int,
    private val userId: Int,
    private val chatsRepository: ChatsRepository,
): ViewModel() {

    private val _chatState = MutableStateFlow(ChatState())
    val chatState = _chatState.asStateFlow()

    init {
        _chatState.update {
            it.copy(
                isLoadingChat = true
            )
        }
        startRefreshingChat()
    }

    private suspend fun getChat() {
        val chat = chatsRepository.getChat(chatId)
        val chatWithReversedMessages = chat.copy(
            messages = chat.messages.reversed()
        )
        _chatState.update {
            it.copy(
                chat = chatWithReversedMessages,
                isLoadingChat = false
            )
        }
    }

    private fun sendMessage() {
        viewModelScope.launch {
            val message = chatsRepository.sendMessage(chatId, userId, chatState.value.typedMessage)
            _chatState.update {
                it.copy(
                    typedMessage = ""
                )
            }
            getChat()
        }
    }

    private fun startRefreshingChat() {
        viewModelScope.launch {
            while (isActive) {
                getChat()
                delay(1000L)
            }
        }
    }

    fun onChatScreenEvent(event: ChatScreenEvent) {
        when (event) {
            is ChatScreenEvent.MessageChanged -> {
               _chatState.update {
                   it.copy(
                       typedMessage = event.message
                   )
               }
            }
            is ChatScreenEvent.SendMessage -> sendMessage()
            is ChatScreenEvent.SelectRating -> {
                _chatState.update {
                    it.copy(
                        selectedRating = event.rating,
                    )
                }
            }
            is ChatScreenEvent.CommentChanged -> {
                _chatState.update {
                    it.copy(
                        comment = event.comment
                    )
                }
            }
            else -> Unit
        }
    }

}