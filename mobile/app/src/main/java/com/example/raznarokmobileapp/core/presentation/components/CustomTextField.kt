package com.example.raznarokapp.core.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.raznarokmobileapp.R
import com.example.raznarokmobileapp.core.domain.UiText
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme

@Composable
fun CustomTextField(
    value: String,
    @DrawableRes icon: Int,
    @StringRes label: Int,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    error: UiText? = null,
    enabled: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        label = {
            Text(text = stringResource(label))
        },
        enabled = enabled,
        leadingIcon = {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        maxLines = 1,
        onValueChange = {
            onValueChange(it)
        },
        isError = error != null,
        supportingText = {
            if (error != null) {
            Text(
                    text = error.asString(),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun CustomTextFieldEmailPreview() {
    RaznarokMobileAppTheme {
        CustomTextField(
            value = "adam@example.com",
            icon = R.drawable.ic_email,
            label = R.string.email,
            onValueChange = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomTextFieldEmailErrorPreview() {
    RaznarokMobileAppTheme {
        CustomTextField(
            value = "adamsdjfios",
            icon = R.drawable.ic_email,
            label = R.string.email,
            onValueChange = { },
            error = UiText.StringResource(R.string.email_invalid)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomTextFieldUsernamePreview() {
    RaznarokMobileAppTheme {
        CustomTextField(
            value = "adam@example",
            icon = R.drawable.ic_user,
            label = R.string.username,
            onValueChange = { }
        )
    }
}
