package com.example.raznarokmobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.raznarokmobileapp.core.presentation.RaznarokAppNavigation
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RaznarokMobileAppTheme {
                RaznarokAppNavigation()
            }
        }
    }
}