package com.example.raznarokmobileapp.guest.presentation.host_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val usersRepository: UsersRepository
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
            val user = usersRepository.getUserById(userId)
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
            else -> TODO("Handle actions")
        }
    }

}