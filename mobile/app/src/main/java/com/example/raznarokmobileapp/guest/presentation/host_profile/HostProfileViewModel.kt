package com.example.raznarokmobileapp.guest.presentation.host_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raznarokmobileapp.chat.data.repository.ChatsRepository
import com.example.raznarokmobileapp.core.data.repository.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HostProfileViewModel(
    private val userId: Int,
    private val hostId: Int,
    private val usersRepository: UsersRepository,
    private val chatsRepository: ChatsRepository
) : ViewModel() {

    private val _hostProfileState = MutableStateFlow(HostProfileState())
    val hostProfileState = _hostProfileState.asStateFlow()

    init {
        getUserById()
    }

    private fun getUserById() {
        _hostProfileState.update {
            it.copy(isLoadingUser = true)
        }
        viewModelScope.launch {
            val user = usersRepository.getUserById(hostId)
            _hostProfileState.update {
                it.copy(
                    user = user,
                    isLoadingUser = false
                )
            }
        }
    }

    fun onHostProfileScreenEvent(event: HostProfileScreenEvent) {
        when (event) {
            is HostProfileScreenEvent.StartChatWithHost -> {
                startChatWithHost(userId, event.hostId)
            }
            is HostProfileScreenEvent.ResetCreatedChatId -> {
                _hostProfileState.update {
                    it.copy(createdChatId = null)
                }
            }
            else -> Unit
        }
    }

    private fun startChatWithHost(userId: Int, hostId: Int) {
        viewModelScope.launch {
            val chatId = chatsRepository.startChatWithHost(userId, hostId)
            _hostProfileState.update {
                it.copy(
                    createdChatId = chatId.id
                )
            }
        }
    }

}