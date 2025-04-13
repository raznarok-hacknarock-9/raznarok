package com.example.raznarokmobileapp.core.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.raznarokmobileapp.R
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme

@Composable
fun ContinueAsScreen(
    onContinueAsGuest: () -> Unit,
    onContinueAsHost: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_big))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            Image(
                painter = painterResource(R.drawable.ic_app_logo),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.choose_your_mode),
                style = MaterialTheme.typography.headlineLarge
            )
            Button(
                onClick = onContinueAsGuest,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_guest),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                    Text(
                        text = stringResource(R.string.continue_as_guest)
                    )
                }
            }
            FilledTonalButton(
                onClick = onContinueAsHost,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_guide),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                    Text(
                        text = stringResource(R.string.continue_as_host)
                    )
                }
            }
        }
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier
//        ) {
//            Icon(
//                painter = painterResource(R.drawable.ic_guest),
//                contentDescription = null,
//                modifier = Modifier.size(256.dp)
//            )
//            FilledTonalButton(
//                onClick = {}
//            ) {
//                Text(
//                    text = stringResource(R.string.continue_as_guest),
//                    style = MaterialTheme.typography.titleLarge,
//                )
//            }
//        }
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Top,
//            modifier = Modifier
//        ) {
//            Icon(
//                painter = painterResource(R.drawable.ic_guide),
//                contentDescription = null,
//                modifier = Modifier.size(256.dp)
//            )
//            FilledTonalButton(
//                onClick = {}
//            ) {
//                Text(
//                    text = stringResource(R.string.continue_as_guide),
//                    style = MaterialTheme.typography.titleLarge,
//                )
//            }
//        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContinueAsScreenPreview() {
    RaznarokMobileAppTheme {
        ContinueAsScreen(
            onContinueAsGuest = {},
            onContinueAsHost = {}
        )
    }
}

