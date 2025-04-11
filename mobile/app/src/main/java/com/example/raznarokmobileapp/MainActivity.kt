package com.example.raznarokmobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.raznarokmobileapp.camera.presentation.CameraCaptureScreen
import com.example.raznarokmobileapp.camera.presentation.CameraPermissionWrapper
import com.example.raznarokmobileapp.core.presentation.UsersViewModel
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RaznarokMobileAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val usersViewModel = koinViewModel<UsersViewModel>()
                    val usersState by usersViewModel.usersState.collectAsStateWithLifecycle()
                    CameraPermissionWrapper {
                        CameraCaptureScreen(
                            null,
                            onImageCaptured = {},
                            modifier = Modifier.padding(innerPadding).fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}