package com.trodar.authentication.registration

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trodar.designtemplate.component.CornerShapeButton
import com.trodar.designtemplate.component.IconEdit
import com.trodar.designtemplate.theme.FireChatTheme
import com.trodar.ui.AppBarRoute
import com.trodar.ui.DevicePreviews
import com.trodar.ui.SocialButtons
import com.trodar.ui.components.ProfileImage

@Composable
fun RegistrationScreen(
    isOffline: Boolean,
    userUiState: UserState,
    onPhotoChange: () -> Unit,
    onLoginChange:(String) -> Unit,
    onEmailChange:(String) -> Unit,
    onPasswordChange:(String) -> Unit,
    onEmailRegisterClick: () -> Unit,
    onGoogleRegisterClick: (String) -> Unit,
    onFaceBookRegisterClick: (String) -> Unit,
    onTwitterRegisterClick: (context: Context) -> Unit,
    onError: (String) -> Unit,
    onBackClick: (() -> Unit)? = null,
) {
    val context = LocalContext.current
    val iconEditHeight = 32.dp
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBarRoute(
            isOffline = isOffline,
            onBackClick = onBackClick
        )
        ProfileImage(
            uri = userUiState.logoUri,
            imageSize = 128.dp,
            editing = true,
            onClick = onPhotoChange,
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(com.trodar.ui.R.string.core_ui_create_account),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))
        IconEdit(
            icon = Icons.Outlined.AccountCircle,
            height = iconEditHeight,
            width = 300.dp,
            radius = 32.dp,
            text = userUiState.userName,
            label = stringResource(com.trodar.ui.R.string.core_ui_username),
            onValueChange = onLoginChange
        )
        Spacer(modifier = Modifier.height(10.dp))
        IconEdit(
            icon = Icons.Outlined.Email,
            height = iconEditHeight,
            width = 300.dp,
            radius = 32.dp,
            text = userUiState.email,
            keyboardType = KeyboardType.Email,
            label = stringResource(com.trodar.ui.R.string.core_ui_email),
            onValueChange = onEmailChange,
        )
        Spacer(modifier = Modifier.height(10.dp))
        IconEdit(
            icon = Icons.Outlined.Password,
            height = iconEditHeight,
            width = 300.dp,
            radius = 32.dp,
            text = userUiState.password,
            keyboardType = KeyboardType.Password,
            label = stringResource(com.trodar.ui.R.string.core_ui_password),
            onValueChange = onPasswordChange,
        )
        Spacer(modifier = Modifier.height(32.dp))
        CornerShapeButton(
            text = stringResource(com.trodar.ui.R.string.core_ui_sign_up),
            onClick = onEmailRegisterClick
        )
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(com.trodar.ui.R.string.core_ui_sign_up_or, "-----------------"),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(16.dp))
        SocialButtons(
            isOffline = isOffline,
            onTwitterClick = { onTwitterRegisterClick(context) },
            onFaceBookRegisterClick = onFaceBookRegisterClick,
            onGoogleRegisterClick = onGoogleRegisterClick,
            onError = onError
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
@DevicePreviews
fun RegistrationScreenPreview() {
    FireChatTheme {
        RegistrationScreen(
            userUiState = UserState("mack", "qwerty"),
            isOffline = false,
            onLoginChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onEmailRegisterClick = {  },
            onGoogleRegisterClick = {},
            onFaceBookRegisterClick = {},
            onError = {},
            onTwitterRegisterClick = {},
            onPhotoChange = {},
        )
    }
}