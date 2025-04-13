package com.example.raznarokmobileapp.core.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.raznarokmobileapp.R
import com.example.raznarokmobileapp.core.domain.model.User
import com.example.raznarokmobileapp.guest.presentation.utils.API_BASE_URL
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme

@Composable
fun UserList(
    users: List<User>,
    onUserClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier
            .padding(top = dimensionResource(R.dimen.padding_medium))
    ) {
        items(users) { user ->
            Card(
                onClick = {
                    onUserClick(user.id)
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                ) {
                    UserAvatar(
                        profilePicture = user.profilePictureFilename
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
                    Column {
                        Text(
                            text = user.firstName,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "${user.commentsAsHost.size} reviews",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserListPreview() {
    RaznarokMobileAppTheme {
        UserList(
            users = listOf(),
            onUserClick = {}
        )
    }
}

