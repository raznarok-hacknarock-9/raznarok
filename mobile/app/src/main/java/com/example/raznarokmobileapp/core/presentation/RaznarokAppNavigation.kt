package com.example.raznarokmobileapp.core.presentation

import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults.contentWindowInsets
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
import com.example.raznarokmobileapp.chat.presentation.chat.ChatScreen
import com.example.raznarokmobileapp.chat.presentation.chat.ChatScreenEvent
import com.example.raznarokmobileapp.chat.presentation.chat.ChatViewModel
import com.example.raznarokmobileapp.chat.presentation.chat_list.ChatListScreen
import com.example.raznarokmobileapp.chat.presentation.chat_list.ChatListScreenEvent
import com.example.raznarokmobileapp.chat.presentation.chat_list.ChatListViewModel
import com.example.raznarokmobileapp.guest.presentation.GuestBottomNavigation
import com.example.raznarokmobileapp.guest.presentation.home.GuestHomeScreen
import com.example.raznarokmobileapp.guest.presentation.home.GuestHomeScreenEvent
import com.example.raznarokmobileapp.guest.presentation.home.GuestHomeViewModel
import com.example.raznarokmobileapp.guest.presentation.host_profile.HostProfileScreen
import com.example.raznarokmobileapp.guest.presentation.host_profile.HostProfileScreenEvent
import com.example.raznarokmobileapp.guest.presentation.host_profile.HostProfileViewModel
import com.example.raznarokmobileapp.host.presentation.HostBottomNavigation
import com.example.raznarokmobileapp.host.presentation.chat.HostChatScreen
import com.example.raznarokmobileapp.host.presentation.chat.HostChatScreenEvent
import com.example.raznarokmobileapp.host.presentation.chat.HostChatViewModel
import com.example.raznarokmobileapp.host.presentation.chat_list.HostChatListScreen
import com.example.raznarokmobileapp.host.presentation.chat_list.HostChatListScreenEvent
import com.example.raznarokmobileapp.host.presentation.chat_list.HostChatListViewModel
import com.example.raznarokmobileapp.host.presentation.profile_edit.HostProfileEditScreen
import com.example.raznarokmobileapp.host.presentation.profile_edit.HostProfileEditViewModel
import com.example.raznarokmobileapp.login.presentation.LoginScreen
import com.example.raznarokmobileapp.login.presentation.LoginViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun RaznarokAppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val guestBottomNavigationScreens = listOf(RaznarokAppScreen.GuestHome, RaznarokAppScreen.Chats)
    val hostBottomNavigationScreens = listOf(RaznarokAppScreen.HostChats, RaznarokAppScreen.HostProfileEdit)
    Scaffold(
        bottomBar = {
            if (currentRoute != null && currentRoute.getScreenFromRoute() in guestBottomNavigationScreens) {
                GuestBottomNavigation(
                    navigationScreens = guestBottomNavigationScreens,
                    currentScreen = currentRoute.getScreenFromRoute(),
                    onNavigate = { screen ->
                        navController.navigate(screen)
                    }
                )
            } else if (currentRoute != null && currentRoute.getScreenFromRoute() in hostBottomNavigationScreens) {
                HostBottomNavigation(
                    navigationScreens = hostBottomNavigationScreens,
                    currentScreen = currentRoute.getScreenFromRoute(),
                    onNavigate = { screen ->
                        navController.navigate(screen)
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
            startDestination = if (loginState.loggedInUser != null) RaznarokAppScreen.ContinueAs else RaznarokAppScreen.Login,
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
                        navController.navigate(RaznarokAppScreen.Host)
                    }
                )
            }
            navigation<RaznarokAppScreen.Guest>(startDestination = RaznarokAppScreen.GuestHome) {
                composable<RaznarokAppScreen.GuestHome> {
                    val guestHomeViewModel = koinViewModel<GuestHomeViewModel>()
                    val guestHomeState by guestHomeViewModel.guestHomeState.collectAsStateWithLifecycle()
                    GuestHomeScreen(
                        loggedUser = loginState.loggedInUser!!,
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
                    val hostProfileViewModel = koinViewModel<HostProfileViewModel>(
                        parameters = {
                            parametersOf(
                                loginState.loggedInUser!!.id,
                                hostProfile.hostId
                            )
                        }
                    )
                    val hostProfileState by hostProfileViewModel.hostProfileState.collectAsStateWithLifecycle()
                    HostProfileScreen(
                        hostProfileState = hostProfileState,
                        onHostProfileScreenEvent = { event ->
                            when (event) {
                                is HostProfileScreenEvent.NavigateToChat -> {
                                    navController.navigate(RaznarokAppScreen.Chat(event.chatId))
                                }
                                else -> Unit
                            }
                            hostProfileViewModel.onHostProfileScreenEvent(event)
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                composable<RaznarokAppScreen.Chats> {
                    val chatListViewModel = koinViewModel<ChatListViewModel>(
                        parameters = { parametersOf(loginState.loggedInUser!!.id) }
                    )
                    val chatListState by chatListViewModel.chatListState.collectAsStateWithLifecycle()
                    ChatListScreen(
                        loggedInUser = loginState.loggedInUser!!,
                        chatListState = chatListState,
                        onChatListScreenEvent = { event ->
                            when (event) {
                                is ChatListScreenEvent.GoToChat -> {
                                    navController.navigate(RaznarokAppScreen.Chat(event.chatId))
                                }
                            }
                            chatListViewModel.onChatListScreenEvent(event)
                        },
                    )
                }
                composable<RaznarokAppScreen.Chat> { backStackEntry ->
                    val chat: RaznarokAppScreen.Chat = backStackEntry.toRoute()
                    val chatViewModel = koinViewModel<ChatViewModel>(
                        parameters = { parametersOf(chat.id, loginState.loggedInUser!!.id) }
                    )
                    val chatState by chatViewModel.chatState.collectAsStateWithLifecycle()
                    ChatScreen(
                        chatState = chatState,
                        onChatScreenEvent = { event ->
                            when (event) {
                                is ChatScreenEvent.GoBack -> {
                                    navController.popBackStack()
                                }
                                else -> Unit
                            }
                            chatViewModel.onChatScreenEvent(event)
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
            navigation<RaznarokAppScreen.Host>(startDestination = RaznarokAppScreen.HostChats) {
                composable<RaznarokAppScreen.HostChats> {
                    val hostChatListViewModel = koinViewModel<HostChatListViewModel>(
                        parameters = { parametersOf(loginState.loggedInUser!!.id) }
                    )
                    val hostChatListState by hostChatListViewModel.hostChatListState.collectAsStateWithLifecycle()
                    HostChatListScreen(
                        loggedInUser = loginState.loggedInUser!!,
                        hostChatListState = hostChatListState,
                        onHostChatListScreenEvent = { event ->
                            when (event) {
                                is HostChatListScreenEvent.GoToChat -> {
                                    navController.navigate(RaznarokAppScreen.HostChat(event.chatId))
                                }
                            }
                            hostChatListViewModel.onChatListScreenEvent(event)
                        },
                    )
                }
                composable<RaznarokAppScreen.HostChat> {
                    val hostChat: RaznarokAppScreen.HostChat = it.toRoute()
                    val hostChatViewModel = koinViewModel<HostChatViewModel>(
                        parameters = { parametersOf(hostChat.id, loginState.loggedInUser!!.id) }
                    )
                    val hostChatState by hostChatViewModel.chatState.collectAsStateWithLifecycle()
                    HostChatScreen(
                        hostChatState = hostChatState,
                        onHostChatScreenEvent = { event ->
                            when (event) {
                                is HostChatScreenEvent.GoBack -> {
                                    navController.popBackStack()
                                }
                                else -> Unit
                            }
                            hostChatViewModel.onHostChatScreenEvent(event)
                        }
                    )
                }
                composable<RaznarokAppScreen.HostProfileEdit> {
                    val hostProfileEditViewModel = koinViewModel<HostProfileEditViewModel>(
                        parameters = {
                            parametersOf(loginState.loggedInUser!!.id)
                        }
                    )
                    val hostProfileEditState by hostProfileEditViewModel.hostProfileEditState.collectAsStateWithLifecycle()
                    HostProfileEditScreen(
                        hostProfileEditState = hostProfileEditState,
                        onHostProfileEditScreenEvent = hostProfileEditViewModel::onHostProfileEditScreenEvent,
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
        "Chats" -> RaznarokAppScreen.Chats
        "Chat" -> RaznarokAppScreen.Chat(1)
        "Host" -> RaznarokAppScreen.Host
        "HostChats" -> RaznarokAppScreen.HostChats
        "HostChat" -> RaznarokAppScreen.HostChat(1)
        "HostProfileEdit" -> RaznarokAppScreen.HostProfileEdit
        else -> RaznarokAppScreen.GuestHome
    }
}