package com.example.raznarokmobileapp.core.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.raznarokmobileapp.R
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme

@Composable
fun SignInWithCard(
    @DrawableRes icon: Int,
    @StringRes label: Int,
    isSignInPending: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        onClick = onClick,
        enabled = !isSignInPending,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_small)
            )
        ) {
            Image(
                painter = painterResource(icon),
                colorFilter = if (isSignInPending) ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }) else null,
                contentDescription = null
            )
            Spacer(
                modifier = Modifier.width(dimensionResource(R.dimen.padding_medium))
            )
            Text(text = stringResource(label))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInWithCardPreview() {
    RaznarokMobileAppTheme {
        SignInWithCard(
            icon = R.drawable.ic_google,
            label = R.string.sign_in_with_google,
            isSignInPending = false,
            onClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInWithCardSignInPendingPreview() {
    RaznarokMobileAppTheme {
        SignInWithCard(
            icon = R.drawable.ic_google,
            label = R.string.sign_in_with_google,
            isSignInPending = true,
            onClick = { }
        )
    }
}
