package com.trodar.authentication.login

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trodar.ui.AppBarRoute
import com.trodar.designtemplate.component.CornerShapeButton
import com.trodar.designtemplate.component.IconEdit
import com.trodar.designtemplate.theme.FireChatTheme
import com.trodar.ui.DevicePreviews
import com.trodar.ui.R
import com.trodar.ui.SocialButtons

@Composable
fun LoginScreen(
    isOffline: Boolean,
    userUiState: UserState,
    onEmailChange:(String) -> Unit,
    onPasswordChange:(String) -> Unit,
    onEmailLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onGoogleClick: (String) -> Unit,
    onFaceBookClick: (String) -> Unit,
    onTwitterClick: (context: Context) -> Unit,
    onError: (String) -> Unit,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBarRoute(isOffline = isOffline)
        Image(
            painter = painterResource(id = R.drawable.core_ui_keren_118),
            contentDescription = "",
            modifier = Modifier.size(96.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.core_ui_welcome),
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.core_ui_login_account),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))
        IconEdit(
            icon = Icons.Outlined.Email,
            height = 32.dp,
            width = 300.dp,
            radius = 32.dp,
            text = userUiState.email,
            label = stringResource(R.string.core_ui_email),
            onValueChange = onEmailChange
        )
        Spacer(modifier = Modifier.height(10.dp))
        IconEdit(
            icon = Icons.Outlined.Password,
            height = 32.dp,
            width = 300.dp,
            radius = 32.dp,
            text = userUiState.password,
            label = stringResource(R.string.core_ui_password),
            keyboardType = KeyboardType.Password,
            onValueChange = onPasswordChange
        ) 

        Spacer(modifier = Modifier.height(32.dp))

        CornerShapeButton(
            text = stringResource(R.string.core_ui_sign_in),
            onClick = onEmailLoginClick
        )
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(R.string.core_ui_sign_in_or, "-----------------"),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(32.dp))

        SocialButtons(
            isOffline = isOffline,
            onTwitterClick = { onTwitterClick(context) } ,
            onFaceBookRegisterClick = onFaceBookClick,
            onGoogleRegisterClick = onGoogleClick,
            onError = onError,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.core_ui_dont_account),
                color = MaterialTheme.colorScheme.onBackground
            )

            TextButton(onClick = onSignUpClick) {
                Text(text = stringResource(R.string.core_ui_sign_up_here))
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}


@Composable
@DevicePreviews
fun LoginScreenPreview() {
    FireChatTheme {
        LoginScreen(
            userUiState = UserState("mack", "qwerty"),
            isOffline = false,
            onSignUpClick = {},
            onTwitterClick = {},
            onPasswordChange = {},
            onEmailChange = {},
            onEmailLoginClick = {},
            onError = {},
            onGoogleClick = {},
            onFaceBookClick = {},
        )
    }
}