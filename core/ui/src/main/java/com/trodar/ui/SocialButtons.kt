package com.trodar.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.trodar.designtemplate.component.CardShadow
import com.trodar.designtemplate.theme.FireChatTheme
import com.trodar.ui.authentification.googleResult

@Composable
fun SocialButtons(
    isOffline: Boolean,
    onTwitterClick: () -> Unit,
    onFaceBookRegisterClick: (String) -> Unit,
    onGoogleRegisterClick: (String) -> Unit,
    onError: (String) -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val callbackManager = remember {
        CallbackManager.Factory.create()
    }
    val fbLauncher = facebookResult(
        callbackManager = callbackManager,
        onSuccess = onFaceBookRegisterClick,
        onError = onError
    )

    Row {
        CardShadow(id = R.drawable.core_ui_google, onClick = {
            noNetworkConnection(
                isOffline = isOffline,
                context = context,
                onError = onError,
            ) {
                googleResult(
                    context = context,
                    scope = scope,
                    onSuccess = onGoogleRegisterClick,
                    onError = onError
                )
            }
        })
        Spacer(modifier = Modifier.width(32.dp))
        CardShadow(
            id = R.drawable.core_ui_facebook,
            onClick = {
                noNetworkConnection(
                    isOffline = isOffline,
                    context = context,
                    onError = onError,
                ) {
                    fbLauncher.launch(listOf("email", "public_profile"))
                }
            }
        )
        Spacer(modifier = Modifier.width(32.dp))
        CardShadow(id = R.drawable.core_ui_twitter, onClick = {
            noNetworkConnection(
                isOffline = isOffline,
                context = context,
                onError = onError,
            ) {
                onTwitterClick()
            }
        }
        )
    }
}

@VisibleForTesting(otherwise = 3)
@Composable
fun facebookResult(
    callbackManager: CallbackManager,
    onSuccess: (String) -> Unit,
    onError: (String) -> Unit
) = rememberLauncherForActivityResult(
    contract =
    LoginManager.getInstance().createLogInActivityResultContract(callbackManager)
) { result ->
    LoginManager.getInstance().onActivityResult(
        result.resultCode,
        result.data,
        object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                onSuccess(result.accessToken.token)
            }
            override fun onCancel() {
            }
            override fun onError(error: FacebookException) {
                onError(error.message.toString())
            }
        }
    )
}

@Preview
@Composable
fun SocialButtonPreview() {
    FireChatTheme {
        SocialButtons(
            isOffline = false,
            {},
            { _ -> },
            { _ -> },
            { _ -> }
        )
    }
}