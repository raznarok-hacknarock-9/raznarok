package com.example.raznarokmobileapp.chat.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.raznarokmobileapp.R
import com.example.raznarokmobileapp.core.domain.model.ChatMessage
import com.example.raznarokmobileapp.core.domain.model.SimpleUser
import com.example.raznarokmobileapp.core.presentation.components.UserAvatar
import com.example.raznarokmobileapp.guest.presentation.utils.formatTimestampToTimeOrNow

@Composable
fun ChatMessageView(
    visitor: SimpleUser,
    host: SimpleUser,
    chatMessage: ChatMessage,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = if (chatMessage.isHostMessage) Alignment.Start else Alignment.End,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        modifier = modifier.width(IntrinsicSize.Max)
    ) {
        if (chatMessage.isHostMessage) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserAvatar(
                    profilePicture = host.profilePictureFilename,
                    size = 48,
                )
                Text(
                    text = host.firstName,
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
        if (chatMessage.isHostMessage) {
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
                content()
            }
        }
        Text(
            text = formatTimestampToTimeOrNow(chatMessage.timestamp),
            style = MaterialTheme.typography.labelMedium,
        )
    }
}