package com.example.raznarokmobileapp.guest.presentation.host_profile

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.raznarokmobileapp.R
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme

@Composable
fun ProfileSection(
    @DrawableRes icon: Int,
    @StringRes label: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_big))
            .fillMaxWidth()
    ) {
        Row {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
            Text(
                text = stringResource(label),
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileSectionPreview() {
    RaznarokMobileAppTheme {
        ProfileSection(
            icon = R.drawable.ic_user,
            label = R.string.about,
        ) {
        }
    }
}

