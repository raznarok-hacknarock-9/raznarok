package com.example.raznarokmobileapp.guest.presentation.home

sealed interface GuestHomeScreenEvent {

    data class LocationChanged(val location: String): GuestHomeScreenEvent

    data class FetchHosts(val dateFrom: Long, val dateTo: Long): GuestHomeScreenEvent

    data class GoToHostProfile(val hostId: Int): GuestHomeScreenEvent
}