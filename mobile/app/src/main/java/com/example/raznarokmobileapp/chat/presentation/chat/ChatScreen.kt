package com.example.raznarokmobileapp.chat.presentation.chat

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.raznarokmobileapp.R
import com.example.raznarokmobileapp.core.domain.model.ChatMessage
import com.example.raznarokmobileapp.core.presentation.LoadingScreen
import com.example.raznarokmobileapp.core.presentation.components.UserAvatar
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    chatState: ChatState,
    onChatScreenEvent: (ChatScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var isCommentDialogVisible by remember { mutableStateOf(false) }

    if (chatState.isLoadingChat || chatState.chat == null) {
        LoadingScreen()
        return
    }

    if (isCommentDialogVisible) {
        AlertDialog(
            onDismissRequest = { isCommentDialogVisible = false },
            confirmButton = {
                TextButton(
                    enabled = chatState.comment.isNotBlank() && chatState.selectedRating > 0,
                    onClick = {
                        onChatScreenEvent(ChatScreenEvent.AddComment)
                        isCommentDialogVisible = false
                    }
                ) {
                    Text(text = stringResource(R.string.comment))
                }
            },
            dismissButton = {
                TextButton(onClick = { isCommentDialogVisible = false }) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
            title = {
                Text(
                    text = stringResource(R.string.rate_your_experience),
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
                ) {
                    StarRatingBar(
                        rating = chatState.selectedRating,
                        onRatingChanged = {
                            onChatScreenEvent(ChatScreenEvent.SelectRating(it))
                        }
                    )
                    Text(text = stringResource(R.string.leave_a_comment))
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = chatState.comment,
                        onValueChange = {
                            onChatScreenEvent(ChatScreenEvent.CommentChanged(it))
                        },
                    )
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
                            profilePicture = chatState.chat.host.profilePictureFilename,
                            size = 48,
                        )
                        Text(
                            text = chatState.chat.host.firstName,

                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onChatScreenEvent(ChatScreenEvent.GoBack)
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
                    .weight(1f)
            ) {
                item {
                    HorizontalDivider(modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small)))
                }
                items(chatState.chat.messages, key = { it.id }) { chatMessage ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                            .fillMaxWidth()
                    ) {
                        ChatMessageView(
                            currentUser = chatState.chat.visitor,
                            otherUser = chatState.chat.host,
                            chatMessage = chatMessage,
                            onDenyCostSuggestion = {
                                onChatScreenEvent(ChatScreenEvent.DenyCostSuggestion(it))
                            },
                            onConfirmCostSuggestion = {
                                onChatScreenEvent(ChatScreenEvent.ConfirmCostSuggestion(it))
                            },
                            onDenyMeetingConfirmation = {
                                onChatScreenEvent(ChatScreenEvent.DenyMeetingConfirmation(it))
                            },
                            onConfirmMeeting = {
                                onChatScreenEvent(ChatScreenEvent.ConfirmMeeting(it))
                            },
                            modifier = Modifier
                                .align(
                                    if (chatMessage.isHostMessage) Alignment.CenterStart else Alignment.CenterEnd,
                                )
                        )
                    }
                }
            }
            MessageInput(
                sendEnabled = chatState.typedMessage.isNotBlank(),
                isCommentButtonEnabled = chatState.chat.isVisitConfirmed,
                message = chatState.typedMessage,
                onMessageChanged = {
                    onChatScreenEvent(ChatScreenEvent.MessageChanged(it))
                },
                onMessageSent = {
                    onChatScreenEvent(ChatScreenEvent.SendMessage)
                },
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            ) {
                IconButton(
                    onClick = { isCommentDialogVisible = true },
                    enabled = chatState.chat.isVisitConfirmed
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_comments),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    RaznarokMobileAppTheme {
        ChatScreen(
            chatState = ChatState(),
            onChatScreenEvent = {}
        )
    }
}