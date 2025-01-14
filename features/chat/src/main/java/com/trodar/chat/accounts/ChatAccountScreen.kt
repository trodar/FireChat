package com.trodar.chat.accounts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.trodar.designtemplate.theme.FireChatTheme
import com.trodar.model.Account
import com.trodar.settings.SettingsRoute
import com.trodar.ui.AppBarRoute
import com.trodar.ui.ContactListPreviewParameterProvider
import com.trodar.ui.DevicePreviews
import com.trodar.ui.components.AccountItem
import com.trodar.ui.components.ShimmerGridAnimation

@Composable
fun ChatAccountScreen(
    accountUiState: AccountUiState,
    onAccountClick: (String) -> Unit,
    showSettingDialog: Boolean,
    onSignOut: () -> Unit,
    onError: (String) -> Unit,
    onShowSettingDialog: () -> Unit,
    onHideSettingDialog: () -> Unit,
) {
    when (accountUiState) {
        is AccountUiState.Error -> {
            onError(accountUiState.exception.message ?: "error")
        }

        AccountUiState.Loading -> {
            ShimmerGridAnimation()
        }

        is AccountUiState.Success -> {

            if (showSettingDialog) {
                SettingsRoute(
                    onSignOut = onSignOut,
                    onHideSettingDialog = onHideSettingDialog,
                )
            }
            LazyColumn(
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ) {
                header(
                    isOffline = accountUiState.isOffline,
                    onShowSettingDialog = onShowSettingDialog,
                )
                accountBody(
                    accounts = accountUiState.accounts,
                    isOffline = accountUiState.isOffline,
                    onAccountClick = onAccountClick,
                    onError = onError
                )
            }
        }
    }
}

fun LazyListScope.header(
    isOffline: Boolean,
    onShowSettingDialog: () -> Unit,
) = item {
    AppBarRoute(
        isOffline = isOffline,
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) { Text("Fire Chat") }
        },
        onSettingClick = onShowSettingDialog,
        onSearchClick = {},
    )
}

fun LazyListScope.accountBody(
    accounts: List<Account>,
    isOffline: Boolean,
    onAccountClick: (String) -> Unit,
    onError: (String) -> Unit,
) = items(
    items = accounts,
    key = { it.accountId },
    itemContent = { account ->
        val context = LocalContext.current
        Box(
            modifier = Modifier.clickable {
                if (isOffline) {
                    onError(context.getString(com.trodar.ui.R.string.core_ui_no_internet))
                }
                onAccountClick(account.accountId)

            }
        ) {
            AccountItem(
                account = account,
                onClick = {}
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
    }
)


@Composable
@DevicePreviews
fun ChatAccountScreenPreview(
    @PreviewParameter(ContactListPreviewParameterProvider::class)
    accounts: List<Account>
) {

    FireChatTheme {
        ChatAccountScreen(
            accountUiState = AccountUiState.Success(accounts, false),
            showSettingDialog = false,
            onAccountClick = {},
            onSignOut = {},
            onError = {},
            onHideSettingDialog = {},
            onShowSettingDialog = {},
        )
    }
}
