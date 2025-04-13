package com.example.raznarokmobileapp.host.presentation.chat

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.raznarokmobileapp.R
import com.example.raznarokmobileapp.chat.presentation.chat.ChatMessageView
import com.example.raznarokmobileapp.chat.presentation.chat.ChatScreenEvent
import com.example.raznarokmobileapp.chat.presentation.chat.MessageInput
import com.example.raznarokmobileapp.chat.presentation.chat.StarRatingBar
import com.example.raznarokmobileapp.core.presentation.LoadingScreen
import com.example.raznarokmobileapp.core.presentation.components.CurrencyIcon
import com.example.raznarokmobileapp.core.presentation.components.UserAvatar
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme
import com.google.firebase.vertexai.type.content

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostChatScreen(
    hostChatState: HostChatState,
    onHostChatScreenEvent: (HostChatScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var isCostDialogVisible by remember { mutableStateOf(false) }

    if (hostChatState.isLoadingChat || hostChatState.chat == null) {
        LoadingScreen()
        return
    }

    if (isCostDialogVisible) {
        AlertDialog(
            onDismissRequest = { isCostDialogVisible = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onHostChatScreenEvent(HostChatScreenEvent.SendCostMessage)
                        isCostDialogVisible = false
                    }
                ) {
                    Text(text = stringResource(R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { isCostDialogVisible = false }) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
            title = {
                Text(
                    text = stringResource(R.string.propose_price),
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
                ) {
                    Text(
                        text = stringResource(R.string.price_detail)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = hostChatState.cost,
                        onValueChange = {
                            onHostChatScreenEvent(HostChatScreenEvent.CostChanged(it))
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        trailingIcon = {
                            CurrencyIcon()
                        }
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val afterCommision = if (hostChatState.cost.toFloatOrNull() == null) {
                            "0"
                        } else {
                            (hostChatState.cost.toFloat() * 0.95).toInt().toString()
                        }
                        Text(
                            text = "After commission you will get $afterCommision"
                        )
                        CurrencyIcon(
                            size = 25
                        )
                    }
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        UserAvatar(
                            profilePicture = hostChatState.chat.visitor.profilePictureFilename,
                            size = 48,
                        )
                        Text(
                            text = hostChatState.chat.visitor.firstName,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onHostChatScreenEvent(HostChatScreenEvent.GoBack)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.go_back)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_flag),
                            contentDescription = stringResource(R.string.report_user)
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                reverseLayout = true,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.padding_medium))
                    .weight(1f)
            ) {
                item {
                    HorizontalDivider(modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small)))
                }
                items(hostChatState.chat.messages, key = { it.id }) { chatMessage ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                            .fillMaxWidth()
                    ) {
                        HostChatMessageView(
                            currentUser = hostChatState.chat.host,
                            otherUser = hostChatState.chat.visitor,
                            chatMessage = chatMessage,
                            modifier = Modifier
                                .align(
                                    if (chatMessage.isHostMessage) Alignment.CenterEnd else Alignment.CenterStart,
                                )
                        )
                    }
                }
            }
            MessageInput(
                sendEnabled = hostChatState.typedMessage.isNotBlank(),
                isCommentButtonEnabled = hostChatState.chat.isVisitConfirmed,
                message = hostChatState.typedMessage,
                onMessageChanged = {
                    onHostChatScreenEvent(HostChatScreenEvent.MessageChanged(it))
                },
                onMessageSent = {
                    onHostChatScreenEvent(HostChatScreenEvent.SendMessage)
                },
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            ) {
                if (hostChatState.chat.isVisitConfirmed && !hostChatState.chat.hasHostCommented && hostChatState.chat.isCostConfirmed) {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_comments),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                } else if (!hostChatState.chat.isVisitConfirmed && hostChatState.chat.isCostConfirmed) {
                    IconButton(
                        onClick = {
                            onHostChatScreenEvent(HostChatScreenEvent.SendMeetingMessage)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_check_circle),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                } else if (!hostChatState.chat.isCostConfirmed) {
                    IconButton(
                        onClick = {
                            isCostDialogVisible = true
                        },
                        enabled = !hostChatState.chat.isCostConfirmed
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_dollar),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    RaznarokMobileAppTheme {
        HostChatScreen(
            hostChatState = HostChatState(),
            onHostChatScreenEvent = {}
        )
    }
}