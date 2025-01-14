package com.trodar.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trodar.ui.components.SelectPhotoBottomSheet

@Composable
fun SettingsRoute(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    onHideSettingDialog: () -> Unit,
    onSignOut: () -> Unit,
) {
    val uiState: SettingsUiState by settingsViewModel.settingsUiState.collectAsStateWithLifecycle()


    when (uiState) {

        is SettingsUiState.Loading -> {}
        is SettingsUiState.Success -> {
            val settings = (uiState as SettingsUiState.Success).settings

            var isClicked by remember {
                mutableStateOf(false)
            }
            if (isClicked) {
                SelectPhotoBottomSheet(
                    onDismiss = { isClicked = false },
                    onSuccess = {
                        isClicked = false
                        settingsViewModel.onLogoChange(it, settings.settings!!.user)
                    },
                )
            }

            SettingsScreen(
                settings = settings,
                onSignOut = onSignOut,
                onHideSettingDialog = onHideSettingDialog,
                onImageChange = { isClicked = true },
                onScanResult = settingsViewModel::scanResultSaved,
                onChangeDynamicColorPreference = settingsViewModel::updateDynamicColorPreference,
                onChangeThemeBrand = settingsViewModel::updateThemeBrand,
                onChangeDarkThemeConfig = settingsViewModel::updateDarkThemeConfig,
            )
        }
    }




}