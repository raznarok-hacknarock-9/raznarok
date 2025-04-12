package com.example.raznarokmobileapp.core.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.raznarokmobileapp.R
import com.example.raznarokmobileapp.core.domain.UiText
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme

@Composable
fun PasswordTextField(
    value: String,
    @StringRes label: Int,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    error: UiText? = null,
    enabled: Boolean = true,
    initialIsPasswordVisible: Boolean = false,
    shouldBeToggleable: Boolean = true,
) {
    var isPasswordVisible by remember { mutableStateOf(initialIsPasswordVisible) }

    OutlinedTextField(
        value = value,
        label = {
            Text(text = stringResource(label))
        },
        enabled = enabled,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_pass),
                contentDescription = null,
            )
        },
        trailingIcon = if (shouldBeToggleable) {
                {
                    val visibilityIcon = if (isPasswordVisible) {
                        R.drawable.ic_visibility_off
                    } else {
                        R.drawable.ic_visibility_on
                    }

                    IconButton(
                        onClick = { isPasswordVisible = !isPasswordVisible }
                    ) {
                        Icon(
                            painter = painterResource(visibilityIcon),
                            contentDescription = null,
                        )
                    }
                }
            } else null,
        visualTransformation =
            if (isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        maxLines = 1,
        onValueChange = onValueChange,
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
private fun PasswordTextFieldPreview() {
    RaznarokMobileAppTheme {
        PasswordTextField(
            value = "Password",
            label = R.string.password,
            onValueChange = { },
            error = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PasswordTextFieldPasswordVisiblePreview() {
    RaznarokMobileAppTheme {
        PasswordTextField(
            value = "Password",
            onValueChange = { },
            label = R.string.password,
            initialIsPasswordVisible = true,
            error = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PasswordTextFieldErrorPreview() {
    RaznarokMobileAppTheme {
        PasswordTextField(
            value = "Password",
            onValueChange = { },
            label = R.string.password,
            initialIsPasswordVisible = true,
            error = UiText.StringResource(R.string.password_too_short)
        )
    }
}
