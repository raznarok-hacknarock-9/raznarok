package com.example.raznarokmobileapp.host.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raznarokmobileapp.chat.data.repository.ChatsRepository
import com.example.raznarokmobileapp.chat.presentation.chat.ChatScreenEvent
import com.example.raznarokmobileapp.chat.presentation.chat.ChatState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class HostChatViewModel(
    private val chatId: Int,
    private val hostId: Int,
    private val chatsRepository: ChatsRepository,
): ViewModel() {

    private val _chatState = MutableStateFlow(HostChatState())
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
            val message = chatsRepository.sendMessage(chatId, hostId, chatState.value.typedMessage)
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

    private fun sendCostMessage() {
        viewModelScope.launch {
            chatsRepository.sendCostMessage(chatId, hostId, chatState.value.cost)
            getChat()
        }
    }

    private fun sendMeetingMessage() {
        viewModelScope.launch {
            chatsRepository.sendMeetingMessage(chatId, hostId)
            getChat()
        }
    }

    fun onHostChatScreenEvent(event: HostChatScreenEvent) {
        when (event) {
            is HostChatScreenEvent.MessageChanged -> {
                _chatState.update {
                    it.copy(
                        typedMessage = event.message
                    )
                }
            }
            is HostChatScreenEvent.SendMessage -> sendMessage()
            is HostChatScreenEvent.CostChanged -> {
                _chatState.update {
                    it.copy(
                        cost = event.cost
                    )
                }
            }
            is HostChatScreenEvent.SendCostMessage -> sendCostMessage()
            is HostChatScreenEvent.SendMeetingMessage -> sendMeetingMessage()
            else -> Unit
        }
    }

}
