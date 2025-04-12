 package com.example.raznarokmobileapp.guest.presentation import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
 import androidx.compose.ui.Modifier
 import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
 import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme

 @Composable
fun GuestHomeScreen(
    guestHomeState: GuestHomeState,
    onGuestHomeScreenEvent: (GuestHomeScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {

}

@Preview
@Composable
private fun Preview() {
    RaznarokMobileAppTheme {
        GuestHomeScreen(
            guestHomeState = GuestHomeState(),
            onGuestHomeScreenEvent = {}
        )
    }
}