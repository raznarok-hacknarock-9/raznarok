package com.example.raznarokmobileapp.host.presentation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.raznarokmobileapp.core.presentation.RaznarokAppScreen
import com.example.raznarokmobileapp.guest.presentation.GuestBottomNavigation
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme

@Composable
fun HostBottomNavigation(
    navigationScreens: List<RaznarokAppScreen>,
    currentScreen: RaznarokAppScreen,
    onNavigate: (RaznarokAppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        navigationScreens.forEach { navigationScreen ->
            NavigationBarItem(
                selected = currentScreen == navigationScreen,
                onClick = { onNavigate(navigationScreen) },
                icon = {
                    Icon(
                        painter = painterResource(navigationScreen.icon!!),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = navigationScreen.title!!,
                    )
                }
            )
        }
    }
}