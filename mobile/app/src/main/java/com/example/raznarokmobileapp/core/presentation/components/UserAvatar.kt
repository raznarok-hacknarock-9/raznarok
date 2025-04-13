package com.example.raznarokmobileapp.core.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.raznarokmobileapp.guest.presentation.utils.API_BASE_URL
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme

@Composable
fun UserAvatar(
    profilePicture: String,
    modifier: Modifier = Modifier,
    size: Int = 64,
) {
    AsyncImage(
        model = "$API_BASE_URL/assets/${profilePicture}",
        contentDescription = null,
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
    )
}

@Preview(showBackground = true)
@Composable
private fun UserAvatarPreview() {
    RaznarokMobileAppTheme {
        UserAvatar(
            profilePicture = "fdsfs"
        )
    }
}

