package com.example.raznarokmobileapp.host.presentation.profile_edit

import com.example.raznarokmobileapp.core.domain.UiText
import com.example.raznarokmobileapp.core.domain.model.User

data class HostProfileEditState(
    val message: UiText? = null,
    val isLoadingUser: Boolean = false,
    val user: User? = null,
)