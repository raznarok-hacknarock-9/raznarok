package com.example.raznarokmobileapp.chat.presentation.chat_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.raznarokmobileapp.R
import com.example.raznarokmobileapp.core.presentation.components.UserAvatar
import com.example.raznarokmobileapp.guest.presentation.utils.timeAgoFromIso
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    chatListState: ChatListState,
    onChatListScreenEvent: (ChatListScreenEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.chats)
                    )
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_big)),
            modifier = Modifier
                .padding(innerPadding)
        ) {
            items(chatListState.chats) { chat ->
                Card(
                    onClick = {
                        onChatListScreenEvent(ChatListScreenEvent.GoToChat(chat.id))
                    },
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
                            vertical = dimensionResource(R.dimen.padding_small),
                            horizontal = dimensionResource(R.dimen.padding_medium)
                        )
                    ) {
                        UserAvatar(
                            profilePicture = chat.host.profilePictureFilename
                        )
                        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
                        Column {
                            Text(
                                text = chat.host.firstName,
                                style = MaterialTheme.typography.titleLarge
                            )
                            chat.messages.lastOrNull()?.let {
                                Text(
                                    text = it.content
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            chat.messages.lastOrNull()?.let {
                                Text(
                                    text = timeAgoFromIso(it.timestamp),
                                )
                            }
                        }
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
        ChatListScreen(
            chatListState = ChatListState(),
            onChatListScreenEvent = {}
        )
    }
}