package com.example.raznarokmobileapp.login.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.example.raznarokapp.core.presentation.components.CustomTextField
import com.example.raznarokmobileapp.R
import com.example.raznarokmobileapp.core.presentation.components.FacebookLoginButton
import com.example.raznarokmobileapp.core.presentation.components.GoogleLoginButton
import com.example.raznarokmobileapp.core.presentation.components.PasswordTextField
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme

@Composable
fun LoginScreen(
    loginState: LoginState,
    onLoginScreenEvent: (LoginScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = loginState.loginError) {
        loginState.loginError?.let { error ->
            snackbarHostState.showSnackbar(error.asString(context))
            onLoginScreenEvent(LoginScreenEvent.ResetState)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.sign_in),
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
                modifier = modifier.weight(1f)
            ) {
                CustomTextField(
                    value = loginState.email,
                    icon = R.drawable.ic_email,
                    label = R.string.email,
                    onValueChange = {
                        onLoginScreenEvent(LoginScreenEvent.EmailChanged(it))
                    }
                )

                PasswordTextField(
                    value = loginState.password,
                    label = R.string.password,
                    onValueChange = {
                        onLoginScreenEvent(LoginScreenEvent.PasswordChanged(it))
                    },
                )

                Button(
                    onClick = {
                        onLoginScreenEvent(LoginScreenEvent.SignInWithEmailAndPassword)
                    },
                    enabled = loginState.email.isNotBlank() && loginState.password.isNotBlank() && !loginState.isLoginPending
                ) {
                    Text(text = stringResource(R.string.sign_in))
                }
//                TextButton(
//                    onClick = {
//                        onLoginScreenEvent(LoginScreenEvent.SignUpInstead)
//                    }
//                ) {
//                    Text(text = stringResource(R.string.sign_up_instead))
//                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.padding_small))
                    .width(IntrinsicSize.Max)
                    .weight(1f),
            ) {
                GoogleLoginButton(
                    isSignInPending = loginState.isLoginPending,
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
                FacebookLoginButton(
                    isSignInPending = loginState.isLoginPending,
                )

                if (loginState.isLoginPending) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(top = dimensionResource(R.dimen.padding_medium))
                            .size(dimensionResource(R.dimen.progress_indicator_size))
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@PreviewScreenSizes
@Composable
private fun LoginScreenPreview() {
    RaznarokMobileAppTheme {
        LoginScreen(
            loginState = LoginState(),
            onLoginScreenEvent = { },
        )
    }
}