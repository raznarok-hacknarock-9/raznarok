package com.example.raznarokmobileapp.guest.presentation.host_profile

import com.example.raznarokmobileapp.core.domain.UiText
import com.example.raznarokmobileapp.core.domain.model.User

data class HostProfileState(
    val message: UiText? = null,
    val isLoadingUser: Boolean = false,
    val user: User? = null,
    val createdChatId: Int? = null
)