package com.example.raznarokmobileapp.host.presentation.chat_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raznarokmobileapp.chat.data.repository.ChatsRepository
import com.example.raznarokmobileapp.chat.presentation.chat_list.ChatListScreenEvent
import com.example.raznarokmobileapp.chat.presentation.chat_list.ChatListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HostChatListViewModel(
    private val hostId: Int,
    private val chatsRepository: ChatsRepository
) : ViewModel() {

    private val _hostChatListState = MutableStateFlow(HostChatListState())
    val hostChatListState = _hostChatListState.asStateFlow()

    init {
        getChats()
    }

    private fun getChats() {
        _hostChatListState.update {
            it.copy(isLoadingChats = true)
        }
        viewModelScope.launch {
            val chats = chatsRepository.getChatsWithHost(hostId)
            _hostChatListState.update {
                it.copy(
                    chats = chats,
                    isLoadingChats = false
                )
            }
        }
    }

    fun onChatListScreenEvent(event: HostChatListScreenEvent) {
        when (event) {
            else -> Unit
        }
    }

}
