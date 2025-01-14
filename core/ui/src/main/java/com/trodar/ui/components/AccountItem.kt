package com.trodar.ui.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.trodar.designtemplate.theme.FireChatTheme
import com.trodar.model.Account
import com.trodar.ui.ContactListPreviewParameterProvider
import com.trodar.ui.DevicePreviews
import com.trodar.ui.extension.toFormatString


@Composable
fun AccountItem(
    modifier: Modifier = Modifier,
    account: Account,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(horizontal = 8.dp),

        ) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProfileImage(
                uri = if (account.logo.isNotEmpty()) Uri.parse(account.logo) else null,
                onClick = onClick
            )
            Box(modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)) {
                Column {
                    Text(
                        text = account.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = account.lastMessage,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = account.lastOnline.toFormatString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}


@Composable
@DevicePreviews
fun ContactItemPreview(
    @PreviewParameter(ContactListPreviewParameterProvider::class)
    accounts: List<Account>
) {
    FireChatTheme {
        Column {
            AccountItem(
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                account = accounts[1],
                onClick = {},
            )
            AccountItem(
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                account = accounts[2],
                onClick = {},
            )
        }
    }
}