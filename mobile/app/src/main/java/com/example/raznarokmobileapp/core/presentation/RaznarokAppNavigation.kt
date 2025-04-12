package com.example.raznarokmobileapp.core.presentation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.raznarokmobileapp.guest.presentation.GuestHomeScreen
import com.example.raznarokmobileapp.guest.presentation.GuestHomeViewModel
import com.example.raznarokmobileapp.login.presentation.LoginScreen
import com.example.raznarokmobileapp.login.presentation.LoginViewModel
import org.koin.androidx.compose.koinViewModel

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
//            if (currentRoute in guestBottomNavigationScreens.map { it.name }) {
//                GuestBottomNavigation(
//                    currentScreen = ChatAppScreen.valueOf(backStackEntry?.destination?.route ?: ChatAppScreen.Messages.name),
//                    bottomNavigationScreens = bottomNavigationScreens,
//                    onNavigate = { screen ->
//                        navController.navigate(screen.name)
//                    }
//                )
//            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = RaznarokAppScreen.Guest,
        ) {
            composable<RaznarokAppScreen.Login> {
                val loginViewModel = koinViewModel<LoginViewModel>()
                val loginState = loginViewModel.loginState
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
            navigation<RaznarokAppScreen.Guest>(startDestination = RaznarokAppScreen.GuestHome) {
                composable<RaznarokAppScreen.GuestHome> {
                    val guestHomeViewModel = koinViewModel<GuestHomeViewModel>()
                    val guestHomeState by guestHomeViewModel.guestHomeState.collectAsStateWithLifecycle()
                    GuestHomeScreen(
                        guestHomeState = guestHomeState,
                        onGuestHomeScreenEvent = guestHomeViewModel::onGuestHomeScreenEvent,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}