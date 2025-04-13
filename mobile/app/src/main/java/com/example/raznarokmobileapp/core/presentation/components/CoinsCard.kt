package com.example.raznarokmobileapp.core.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.raznarokmobileapp.R
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme

@Composable
fun CoinsCard(
    coins: Int,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    start = dimensionResource(R.dimen.padding_small),
                    end = dimensionResource(R.dimen.padding_tiny)
                )
                .padding(
                    vertical = dimensionResource(R.dimen.padding_tiny)
                )
        ) {
            Text(
                text = coins.toString(),
            )
            CurrencyIcon()
        }
    }
}