package com.example.raznarokmobileapp.host.presentation.profile_edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.raznarokmobileapp.core.presentation.components.UserAvatar
import com.example.raznarokmobileapp.core.presentation.components.UserReviewsInfo
import com.example.raznarokmobileapp.guest.presentation.host_profile.HostProfileScreenEvent
import com.example.raznarokmobileapp.guest.presentation.host_profile.ProfileSection
import com.example.raznarokmobileapp.guest.presentation.host_profile.ProfileTags
import com.example.raznarokmobileapp.guest.presentation.utils.API_BASE_URL
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme

@Composable
fun HostProfileEditScreen(
    hostProfileEditState: HostProfileEditState,
    onHostProfileEditScreenEvent: (HostProfileEditScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        if (hostProfileEditState.user != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(innerPadding).fillMaxSize().verticalScroll(scrollState)
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
                        model = "$API_BASE_URL/assets/${hostProfileEditState.user.profilePictureFilename}",
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
                    text = hostProfileEditState.user.firstName,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))
                )
                UserReviewsInfo(
                    commentsAsHost = hostProfileEditState.user.commentsAsHost
                )
                ProfileTags(
                    tags = hostProfileEditState.user.tags,
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium)))
                ProfileSection(
                    icon = R.drawable.ic_user,
                    label = R.string.about,
                    trailingContent = {
                        TextButton(
                            onClick = {}
                        ) {
                            Text(
                                text = stringResource(R.string.edit)
                            )
                        }
                    }
                ) {
                    Text(
                        text = hostProfileEditState.user.descriptionAsHost,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium)))
                ProfileSection(
                    icon = R.drawable.ic_availability,
                    label = R.string.availability,
                    trailingContent = {
                        TextButton(
                            onClick = {}
                        ) {
                            Text(
                                text = stringResource(R.string.edit)
                            )
                        }
                    }
                ) {
                    Column {
                        hostProfileEditState.user.availabilities.forEach { availability ->
                            Text(
                                text = availability.toFormattedString(),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))
                            )
                        }
                    }
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium)))
                ProfileSection(
                    icon = R.drawable.ic_comments,
                    label = R.string.reviews,
                ) {
                    hostProfileEditState.user.commentsAsHost.forEach { comment ->
                        ListItem(
                            leadingContent = {
                                UserAvatar(
                                    profilePicture = comment.author.profilePictureFilename
                                )
                            },
                            headlineContent = {
                                Text(
                                    text = comment.author.firstName,
                                    fontWeight = FontWeight.Bold,
                                )
                            },
                            supportingContent = {
                                Text(
                                    text = comment.content,
                                )
                            }
                        )
                        HorizontalDivider()
                    }
                }
                Spacer(modifier = Modifier.height(128.dp))
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
        HostProfileEditScreen(
            hostProfileEditState = HostProfileEditState(),
            onHostProfileEditScreenEvent = {}
        )
    }
}