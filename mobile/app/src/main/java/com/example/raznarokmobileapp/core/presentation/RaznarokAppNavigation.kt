package com.example.raznarokmobileapp.core.presentation

import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.raznarokmobileapp.guest.presentation.GuestBottomNavigation
import com.example.raznarokmobileapp.guest.presentation.home.GuestHomeScreen
import com.example.raznarokmobileapp.guest.presentation.home.GuestHomeScreenEvent
import com.example.raznarokmobileapp.guest.presentation.home.GuestHomeViewModel
import com.example.raznarokmobileapp.guest.presentation.host_profile.HostProfileScreen
import com.example.raznarokmobileapp.guest.presentation.host_profile.HostProfileScreenEvent
import com.example.raznarokmobileapp.guest.presentation.host_profile.HostProfileViewModel
import com.example.raznarokmobileapp.login.presentation.LoginScreen
import com.example.raznarokmobileapp.login.presentation.LoginViewModel
import io.ktor.http.parameters
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun RaznarokAppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val guestBottomNavigationScreens = listOf(RaznarokAppScreen.GuestHome)
    Scaffold(
        bottomBar = {
            if (currentRoute != null && currentRoute.getScreenFromRoute() in guestBottomNavigationScreens) {
                GuestBottomNavigation(
                    navigationScreens = guestBottomNavigationScreens,
                    currentScreen = currentRoute.getScreenFromRoute(),
                    onNavigate = { screen ->
                        navController.navigate(screen.route)
                    }
                )
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        val loginViewModel = koinViewModel<LoginViewModel>()
        val loginState = loginViewModel.loginState
        NavHost(
            navController = navController,
            startDestination = RaznarokAppScreen.Guest,
        ) {
            composable<RaznarokAppScreen.Login> {
                LoginScreen(
                    loginState = loginState,
                    onLoginScreenEvent = loginViewModel::onLoginScreenEvent,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            composable<RaznarokAppScreen.ContinueAs> {
                ContinueAsScreen(
                    onContinueAsGuest = {
                        navController.navigate(RaznarokAppScreen.Guest)
                    },
                    onContinueAsHost = {
                        navController.navigate(RaznarokAppScreen.Login)
                    }
                )
            }
            navigation<RaznarokAppScreen.Guest>(startDestination = RaznarokAppScreen.HostProfile(2)) {
                composable<RaznarokAppScreen.GuestHome> {
                    val guestHomeViewModel = koinViewModel<GuestHomeViewModel>()
                    val guestHomeState by guestHomeViewModel.guestHomeState.collectAsStateWithLifecycle()
                    GuestHomeScreen(
                        guestHomeState = guestHomeState,
                        onGuestHomeScreenEvent = { event ->
                            when (event) {
                                is GuestHomeScreenEvent.GoToHostProfile -> {
                                    navController.navigate(RaznarokAppScreen.HostProfile(event.hostId))
                                }
                                else -> Unit
                            }
                            guestHomeViewModel.onGuestHomeScreenEvent(event)
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                composable<RaznarokAppScreen.HostProfile> { backStackEntry ->
                    val hostProfile: RaznarokAppScreen.HostProfile = backStackEntry.toRoute()
                    val hostProfileViewModel = koinViewModel<HostProfileViewModel>(parameters = { parametersOf(hostProfile.hostId) })
                    val hostProfileState by hostProfileViewModel.hostProfileState.collectAsStateWithLifecycle()
                    HostProfileScreen(
                        hostProfileState = hostProfileState,
                        onHostProfileScreenEvent = { event ->
                            hostProfileViewModel.onHostProfileScreenEvent(event)
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

fun String.getScreenFromRoute(): RaznarokAppScreen {
    val screenRoute = this.substringAfterLast('.').substringBefore('/')
    return when (screenRoute) {
        "Login" -> RaznarokAppScreen.Login
        "ContinueAs" -> RaznarokAppScreen.ContinueAs
        "Guest" -> RaznarokAppScreen.Guest
        "GuestHome" -> RaznarokAppScreen.GuestHome
        "HostProfile" -> RaznarokAppScreen.HostProfile(1)
        else -> RaznarokAppScreen.GuestHome
    }
}