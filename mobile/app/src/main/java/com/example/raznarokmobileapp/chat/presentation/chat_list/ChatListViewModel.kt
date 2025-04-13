package com.example.raznarokmobileapp.chat.presentation.chat_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raznarokmobileapp.chat.data.repository.ChatsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val userId: Int,
    private val chatsRepository: ChatsRepository
) : ViewModel() {

    private val _chatListState = MutableStateFlow(ChatListState())
    val chatListState = _chatListState.asStateFlow()

    init {
        getChats()
    }

    private fun getChats() {
        _chatListState.update {
            it.copy(isLoadingChats = true)
        }
        viewModelScope.launch {
            val chats = chatsRepository.getChats(userId)
            _chatListState.update {
                it.copy(
                    chats = chats,
                    isLoadingChats = false
                )
            }
        }
    }

    fun onChatListScreenEvent(event: ChatListScreenEvent) {
        when (event) {
            else -> Unit
        }
    }

}