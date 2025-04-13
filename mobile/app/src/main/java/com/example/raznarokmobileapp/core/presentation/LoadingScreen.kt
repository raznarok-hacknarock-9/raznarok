package com.example.raznarokmobileapp.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingScreenPreview() {
    RaznarokMobileAppTheme {
        LoadingScreen()
    }
}

