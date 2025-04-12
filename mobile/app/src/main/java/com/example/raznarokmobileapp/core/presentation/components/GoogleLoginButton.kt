package com.example.raznarokmobileapp.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.raznarokmobileapp.R

@Composable
fun GoogleLoginButton(
    isSignInPending: Boolean,
//    onSignInResult: (Result<UserData, SignInError>) -> Unit,
//    onSignInWithCredential: (AuthCredential) -> Unit,
    modifier: Modifier = Modifier,
//    isInPreviewMode: Boolean = false,
) {
//    val googleAuthUiClient = if (isInPreviewMode) null else GoogleAuthUiClient(
//        context = LocalContext.current.applicationContext,
//        oneTapClient = Identity.getSignInClient(LocalContext.current.applicationContext),
//        onSignInWithCredential = onSignInWithCredential,
//        onSignInResult = onSignInResult,
//    )
//
//    val scope = rememberCoroutineScope()
//    val googleAuthLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartIntentSenderForResult(),
//        onResult = { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                scope.launch {
//                    googleAuthUiClient?.signInWithIntent(
//                        intent = result.data ?: return@launch
//                    )
//                }
//            }
//        }
//    )

    SignInWithCard(
        icon = R.drawable.ic_google,
        label = R.string.sign_in_with_google,
        isSignInPending = isSignInPending,
        onClick = {
            if (!isSignInPending) {
//                scope.launch {
//                    val signInIntentSender = googleAuthUiClient?.signIn()
//                    googleAuthLauncher.launch(
//                        IntentSenderRequest.Builder(
//                            signInIntentSender ?: return@launch
//                        ).build()
//                    )
//                }
            }
        },
        modifier = modifier.fillMaxWidth(),
    )
}