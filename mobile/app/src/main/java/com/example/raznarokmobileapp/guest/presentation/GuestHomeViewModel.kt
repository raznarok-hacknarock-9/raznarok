package com.example.raznarokmobileapp.guest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class GuestHomeViewModel : ViewModel(

) {

    private val _guestHomeState = MutableStateFlow(GuestHomeState())
    val guestHomeState = _guestHomeState
        .onStart {
            // load data
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = GuestHomeState()
        )

    fun onGuestHomeScreenEvent(event: GuestHomeScreenEvent) {
        when (event) {
            else -> TODO("Handle actions")
        }
    }

}