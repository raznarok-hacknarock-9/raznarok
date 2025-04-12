package com.example.raznarokmobileapp.guest.presentation.host_profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.raznarokmobileapp.R
import com.example.raznarokmobileapp.core.domain.model.toFormattedString
import com.example.raznarokmobileapp.guest.presentation.utils.API_BASE_URL
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme
import com.google.firebase.vertexai.type.content

@Composable
fun HostProfileScreen(
    hostProfileState: HostProfileState,
    onHostProfileScreenEvent: (HostProfileScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            hostProfileState.user?.let {
                ExtendedFloatingActionButton(
                    onClick = {
                        onHostProfileScreenEvent(HostProfileScreenEvent.StartChatWithHost(hostProfileState.user.id))
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_chat),
                            contentDescription = null
                        )
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.start_chat)
                        )
                    },
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        if (hostProfileState.user != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(innerPadding).fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.image_holidays_sunny),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                    AsyncImage(
                        model = "${API_BASE_URL}/assets/${hostProfileState.user.profilePictureFilename}",
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(150.dp)
                            .align(Alignment.BottomCenter)
                            .offset(y = 75.dp)
                            .clip(CircleShape)
                            .border(4.dp, Color.White, CircleShape)
                            .background(Color.LightGray)
                            .zIndex(1f)
                    )
                }
                Spacer(modifier = Modifier.height(75.dp))
                Text(
                    text = hostProfileState.user.firstName,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))
                )
                Text(
                    text = "${hostProfileState.user.commentsAsHost.size} reviews",
                )
                ProfileTags(
                    tags = hostProfileState.user.tags,
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium)))
                ProfileSection(
                    icon = R.drawable.ic_user,
                    label = R.string.about
                ) {
                    Text(
                        text = hostProfileState.user.descriptionAsHost,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium)))
                ProfileSection(
                    icon = R.drawable.ic_availability,
                    label = R.string.availability
                ) {
                    LazyColumn {
                        items(hostProfileState.user.availabilities) { availability ->
                            Text(
                                text = availability.toFormattedString(),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))
                            )
                        }
                    }
                }
            }
        } else {
            CircularProgressIndicator(
                modifier = Modifier.size(128.dp)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    RaznarokMobileAppTheme {
        HostProfileScreen(
            hostProfileState = HostProfileState(),
            onHostProfileScreenEvent = {}
        )
    }
}