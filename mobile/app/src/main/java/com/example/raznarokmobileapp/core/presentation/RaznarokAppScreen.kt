package com.example.raznarokmobileapp.core.presentation

import com.example.raznarokmobileapp.R
import kotlinx.serialization.Serializable

@Serializable
sealed class RaznarokAppScreen(
    val route: String,
    val title: String? = null,
    val icon: Int? = null
) {

    @Serializable
    data object Login: RaznarokAppScreen("Login")

    @Serializable
    data object ContinueAs: RaznarokAppScreen("ContinueAs")

    @Serializable
    data object Guest: RaznarokAppScreen("Guest")

    @Serializable
    data object GuestHome: RaznarokAppScreen("GuestHome", "Home", R.drawable.ic_home)

    @Serializable
    data class HostProfile(val hostId: Int): RaznarokAppScreen("GuestProfile")

}