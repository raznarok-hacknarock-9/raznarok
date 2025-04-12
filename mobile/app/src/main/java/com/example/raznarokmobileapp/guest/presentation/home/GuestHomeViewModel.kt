package com.example.raznarokmobileapp.guest.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raznarokmobileapp.core.data.repository.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GuestHomeViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _guestHomeState = MutableStateFlow(GuestHomeState())
    val guestHomeState = _guestHomeState.asStateFlow()

    fun onGuestHomeScreenEvent(event: GuestHomeScreenEvent) {
        when (event) {
            is GuestHomeScreenEvent.LocationChanged -> {
                _guestHomeState.update {
                    it.copy(
                        searchQuery = event.location
                    )
                }
            }
            is GuestHomeScreenEvent.FetchHosts -> fetchHosts(event.dateFrom, event.dateTo)
            else -> Unit
        }
    }

    private fun fetchHosts(dateFrom: Long, dateTo: Long) {
        _guestHomeState.update {
            it.copy(
                isFetchingHosts = true
            )
        }
        viewModelScope.launch {
            val hosts = usersRepository.fetchHosts(
                location = _guestHomeState.value.searchQuery,
                dateFrom = dateFrom,
                dateTo = dateTo
            )
            _guestHomeState.update {
                it.copy(
                    searchedLocation = it.searchQuery,
                    availableHosts = hosts,
                    isFetchingHosts = false
                )
            }
        }
    }

}