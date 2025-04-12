package com.example.raznarokmobileapp.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.raznarokmobileapp.R

@Composable
fun FacebookLoginButton(
    isSignInPending: Boolean,
//    onSignInWithCredential: (AuthCredential) -> Unit,
//    onSignInResult: (Result<UserData, SignInError>) -> Unit,
    modifier: Modifier = Modifier,
//    isInPreviewMode: Boolean = false,
) {
//    val launcher = if (!isInPreviewMode) {
//        val scope = rememberCoroutineScope()
//        val loginManager = LoginManager.getInstance()
//        val callbackManager = remember {
//            CallbackManager.Factory.create()
//        }
//        val launcher = rememberLauncherForActivityResult(
//            loginManager.createLogInActivityResultContract(callbackManager, null)
//        ) {}
//
//        DisposableEffect(Unit) {
//            loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
//                override fun onCancel() {
//                    onSignInResult(Result.Error(SignInWithCredentialError.SIGN_IN_CANCELLED))
//                }
//
//                override fun onError(error: FacebookException) {
//                    onSignInResult(Result.Error(SignInWithCredentialError.FACEBOOK_ERROR))
//                }
//
//                override fun onSuccess(result: LoginResult) {
//                    scope.launch {
//                        val token = result.accessToken.token
//                        val facebookCredentials = FacebookAuthProvider.getCredential(token)
//                        onSignInWithCredential(facebookCredentials)
//                    }
//                }
//            })
//
//            onDispose {
//                loginManager.unregisterCallback(callbackManager)
//            }
//        }
//        launcher
//    } else {
//        null
//    }

    SignInWithCard(
        icon = R.drawable.ic_facebook,
        label = R.string.sign_in_with_facebook,
        isSignInPending = isSignInPending,
        onClick = {
            if (!isSignInPending) {
//                launcher?.launch(listOf("email", "public_profile"))
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}