package com.trodar.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.trodar.model.Account
import com.trodar.ui.PreviewParameterData.accounts
import kotlinx.datetime.Clock

class ContactListPreviewParameterProvider: PreviewParameterProvider<List<Account>> {

    override val values: Sequence<List<Account>> = sequenceOf(accounts)
}

object PreviewParameterData {
    val accounts: List<Account> = listOf(
        Account(
            accountId = "1",
            name = "street@gmail.com",
            logo = "",
            lastOnline = Clock.System.now().toEpochMilliseconds(),
            notReadCount = 0,
            lastAccountId = "",
            lastMessage = "",
        ),
        Account(
            accountId = "2",
            name = "street@gmail.com",
            logo = "",
            lastOnline = Clock.System.now().toEpochMilliseconds(),
            notReadCount = 0,
            lastAccountId = "",
            lastMessage = "",
        ),
        Account(
            accountId = "3",
            name = "good_bye_very_long_text_for_test@gmail.com",
            logo = "https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/account-icon.png",
            lastOnline = Clock.System.now().toEpochMilliseconds(),
            notReadCount = 2,
            lastAccountId = "",
            lastMessage = "Hello User",
        ),
        Account(
            accountId = "4",
            name = "wolf",
            logo = "https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/silhouette-person-icon.png",
            lastOnline = Clock.System.now().toEpochMilliseconds(),
            notReadCount = 1,
            lastAccountId = "",
            lastMessage = "",
        ),
    )
}