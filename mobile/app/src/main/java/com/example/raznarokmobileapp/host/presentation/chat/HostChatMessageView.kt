package com.example.raznarokmobileapp.host.presentation.chat

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.raznarokmobileapp.R
import com.example.raznarokmobileapp.core.domain.model.ChatMessage
import com.example.raznarokmobileapp.core.domain.model.SimpleUser
import com.example.raznarokmobileapp.core.presentation.components.CurrencyIcon
import com.example.raznarokmobileapp.core.presentation.components.UserAvatar
import com.example.raznarokmobileapp.guest.presentation.utils.formatTimestampToTimeOrNow
import io.ktor.client.utils.EmptyContent.status

@Composable
fun HostChatMessageView(
    currentUser: SimpleUser,
    otherUser: SimpleUser,
    chatMessage: ChatMessage,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = if (chatMessage.isHostMessage) Alignment.End else Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        modifier = modifier.width(IntrinsicSize.Max)
    ) {
        if (!chatMessage.isHostMessage) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserAvatar(
                    profilePicture = otherUser.profilePictureFilename,
                    size = 48,
                )
                Text(
                    text = otherUser.firstName,
                )
            }
        }
        val content: @Composable () -> Unit = {
            Text(
                text = chatMessage.content,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(R.dimen.padding_medium),
                    vertical = dimensionResource(R.dimen.padding_small)
                )
            )
        }
        val costMessageContent: @Composable () -> Unit = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(R.dimen.padding_big),
                    vertical = dimensionResource(R.dimen.padding_big)
                )
            ) {
                Text(
                    text = stringResource(R.string.suggested_cost),
                    style = MaterialTheme.typography.titleLarge,
                )
                Row {
                    Text(
                        text = chatMessage.content,
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    CurrencyIcon(size = 40)
                }
                AnimatedContent(
                    targetState = chatMessage.status,
                ) { status ->
                    when (status) {
                        "confirmed" -> {
                            Row {
                                Icon(
                                    painter = painterResource(R.drawable.ic_check),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
                                Text(
                                    text = stringResource(R.string.guest_confirmed)
                                )
                            }
                        }
                        "pending" -> {
                            Row {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
                                Text(
                                    text = stringResource(R.string.pending_confirmation)
                                )
                            }
                        }
                        "canceled" -> {
                            Row {
                                Icon(
                                    painter = painterResource(R.drawable.ic_cancelled),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
                                Text(
                                    text = stringResource(R.string.user_canceled)
                                )
                            }
                        }
                    }
                }
            }
        }
        val meetingMessageContent: @Composable () -> Unit = {
            AnimatedContent(
                targetState = chatMessage.status,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(R.dimen.padding_big),
                    vertical = dimensionResource(R.dimen.padding_big)
                )
            ) { status ->
                when (status) {
                    "confirmed" -> {
                        Row {
                            Icon(
                                painter = painterResource(R.drawable.ic_check),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
                            Text(
                                text = stringResource(R.string.meeting_confirmed)
                            )
                        }
                    }
                    "pending" -> {
                        Row {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
                            Text(
                                text = stringResource(R.string.pending_meeting_confirmation)
                            )
                        }
                    }
                    "canceled" -> {
                        Row {
                            Icon(
                                painter = painterResource(R.drawable.ic_cancelled),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
                            Text(
                                text = stringResource(R.string.visitor_cancelled)
                            )
                        }
                    }
                }
            }
        }
        if (!chatMessage.isHostMessage) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = dimensionResource(R.dimen.padding_medium)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                content()
            }
        } else {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (chatMessage.type == "cost") {
                    costMessageContent()
                } else if (chatMessage.type == "meeting") {
                    meetingMessageContent()
                } else {
                    content()
                }
            }
        }
        Text(
            text = formatTimestampToTimeOrNow(chatMessage.timestamp),
            style = MaterialTheme.typography.labelMedium,
        )
    }
}