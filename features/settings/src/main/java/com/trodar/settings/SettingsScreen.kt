package com.trodar.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.Logout
import androidx.compose.material.icons.sharp.AutoMode
import androidx.compose.material.icons.sharp.DarkMode
import androidx.compose.material.icons.sharp.LightMode
import androidx.compose.material.icons.sharp.QrCodeScanner
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.trodar.designtemplate.theme.FireChatTheme
import com.trodar.model.Account
import com.trodar.model.DarkThemeConfig
import com.trodar.model.SettingsData
import com.trodar.model.ThemeBrand
import com.trodar.ui.ContactListPreviewParameterProvider
import com.trodar.ui.DevicePreviews
import com.trodar.ui.components.ProfileImage

@Composable
fun SettingsScreen(
    settings: SettingData,
    onHideSettingDialog: () -> Unit,
    onImageChange: () -> Unit,
    onSignOut: () -> Unit,
    onScanResult: (String) -> Unit,
    onChangeThemeBrand: (themeBrand: ThemeBrand) -> Unit,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit,
) {
    val configuration = LocalConfiguration.current

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
        onDismissRequest = onHideSettingDialog,
        title = {
            SettingTitle(
                settings = settings.settings!!,
                onImageChange = onImageChange,
                onSignOut = onSignOut,
                onChangeThemeBrand = onChangeThemeBrand,
                onChangeDarkThemeConfig = onChangeDarkThemeConfig,
                onChangeDynamicColorPreference = onChangeDynamicColorPreference,
            )
        },
        text = {
            SettingBody(
                scanResult = settings.scanResult,
                onScanResult = onScanResult,
            )
        },
        confirmButton = {
            Button(
                onClick = onHideSettingDialog,
                modifier = Modifier.padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = stringResource(com.trodar.ui.R.string.core_ui_ok),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

        }
    )
}

@Composable
fun SettingTitle(
    settings: SettingsData,
    onImageChange: () -> Unit,
    onSignOut: () -> Unit,
    onChangeThemeBrand: (themeBrand: ThemeBrand) -> Unit,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProfileImage(
                uri = Uri.parse(settings.user.logo),
                editing = true,
                onClick = onImageChange,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Eugene", color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.weight(1f))

            IconButton(onSignOut) {
                Icon(
                    imageVector = Icons.AutoMirrored.Sharp.Logout,
                    contentDescription = "Logout",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = {
                    if (settings.themeBrand == ThemeBrand.DEFAULT) {
                        onChangeThemeBrand(ThemeBrand.ANDROID)
                    } else {
                        onChangeThemeBrand(ThemeBrand.DEFAULT)
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = if (settings.themeBrand == ThemeBrand.DEFAULT)
                        MaterialTheme.colorScheme.onSecondary
                    else
                        MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Sharp.AutoMode,
                    contentDescription = "Logout",
                    tint = if (settings.themeBrand == ThemeBrand.DEFAULT)
                        MaterialTheme.colorScheme.secondary
                    else
                        MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = { onChangeDynamicColorPreference(!settings.useDynamicColor) },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = if (settings.useDynamicColor)
                        MaterialTheme.colorScheme.onSecondary
                    else
                        MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.night_light),
                    contentDescription = "Logout",
                    tint = if (settings.useDynamicColor)
                        MaterialTheme.colorScheme.secondary
                    else
                        MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = {
                    onChangeDarkThemeConfig(DarkThemeConfig.LIGHT)
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = if (settings.darkThemeConfig != DarkThemeConfig.DARK)
                        MaterialTheme.colorScheme.onSecondary
                    else
                        MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Sharp.LightMode,
                    contentDescription = "Logout",
                    tint = if (settings.darkThemeConfig != DarkThemeConfig.DARK)
                        MaterialTheme.colorScheme.secondary
                    else
                        MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = {
                    onChangeDarkThemeConfig(DarkThemeConfig.DARK)
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = if (settings.darkThemeConfig == DarkThemeConfig.DARK)
                        MaterialTheme.colorScheme.onSecondary
                    else
                        MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Sharp.DarkMode,
                    contentDescription = "Dark mode",
                    tint = if (settings.darkThemeConfig == DarkThemeConfig.DARK)
                        MaterialTheme.colorScheme.secondary
                    else
                        MaterialTheme.colorScheme.primary
                )

            }
        }
    }
}


@Composable
fun SettingBody(
    scanResult: String?,
    onScanResult: (String) -> Unit,
) {
    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = {
            if (it.contents != null)
                onScanResult(it.contents)
        }
    )

    HorizontalDivider()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        IconButton(
            modifier = Modifier.size(128.dp),
            onClick = { scanLauncher.launch(ScanOptions()) }
        ) {
            Icon(
                imageVector = Icons.Sharp.QrCodeScanner,
                contentDescription = "Qr Scanner",
                modifier = Modifier.size(128.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(com.trodar.ui.R.string.core_ui_scna_result),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = scanResult ?: "",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}


@Composable
@DevicePreviews
fun SettingsScreenPreview(
    @PreviewParameter(ContactListPreviewParameterProvider::class)
    accounts: List<Account>
) {
    FireChatTheme {
        SettingsScreen(
            settings = SettingData(),
            onSignOut = {},
            onHideSettingDialog = {},
            onImageChange = {},
            onScanResult = {},
            onChangeDynamicColorPreference = {},
            onChangeThemeBrand = {},
            onChangeDarkThemeConfig = {},
        )
    }
}



























