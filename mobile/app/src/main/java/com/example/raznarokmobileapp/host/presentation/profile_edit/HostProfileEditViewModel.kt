package com.example.raznarokmobileapp.host.presentation.profile_edit

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

class HostProfileEditViewModel(
    private val hostId: Int,
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _hostProfileEditState = MutableStateFlow(HostProfileEditState())
    val hostProfileEditState = _hostProfileEditState.asStateFlow()

    init {
        getUserById()
    }

    private fun getUserById() {
        _hostProfileEditState.update {
            it.copy(isLoadingUser = true)
        }
        viewModelScope.launch {
            val user = usersRepository.getUserById(hostId)
            _hostProfileEditState.update {
                it.copy(
                    user = user,
                    isLoadingUser = false
                )
            }
        }
    }

    fun onHostProfileEditScreenEvent(event: HostProfileEditScreenEvent) {
        when (event) {
            else -> TODO("Handle actions")
        }
    }

}