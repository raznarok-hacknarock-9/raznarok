package com.example.raznarokmobileapp.guest.presentation.host_profile

sealed interface HostProfileScreenEvent {

    data class StartChatWithHost(val hostId: Int): HostProfileScreenEvent

    data class NavigateToChat(val chatId: Int): HostProfileScreenEvent

    data object ResetCreatedChatId: HostProfileScreenEvent
}