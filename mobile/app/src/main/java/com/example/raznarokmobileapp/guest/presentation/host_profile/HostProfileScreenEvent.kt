package com.example.raznarokmobileapp.guest.presentation.host_profile

sealed interface HostProfileScreenEvent {

    data class StartChatWithHost(val hostId: Int): HostProfileScreenEvent
}