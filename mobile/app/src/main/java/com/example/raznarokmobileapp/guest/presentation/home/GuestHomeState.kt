package com.example.raznarokmobileapp.guest.presentation.home

import com.example.raznarokmobileapp.core.domain.UiText
import com.example.raznarokmobileapp.core.domain.model.User

data class GuestHomeState(
    val message: UiText? = null,
    val searchQuery: String = "Barcelona",
    val searchedLocation: String? = null,
    val isFetchingHosts: Boolean = false,
    val availableHosts: List<User> = listOf()
)