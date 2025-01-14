package com.trodar.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material.icons.sharp.Call
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material.icons.sharp.MoreVert
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material.icons.twotone.SignalWifiOff
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarRoute(
    modifier: Modifier = Modifier,
    isOffline: Boolean,
    title: (@Composable () -> Unit) = { },
    onBackClick: (() -> Unit)? = null,
    onSettingClick: (() -> Unit)? = null,
    onSearchClick: (() -> Unit)? = null,
    onSettingsActionClick: (() -> Unit)? = null,
    onCallClick: (() -> Unit)? = null,
) {


    val navBackIcon = @Composable {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                        contentDescription = "navigationIconContentDescription",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            } else if (onSettingClick != null) {
                IconButton(onClick = onSettingClick) {
                    Icon(
                        imageVector = Icons.Sharp.Menu,
                        contentDescription = "navigationIconContentDescription",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }

            if (isOffline) {
                val delayTime = 500L
                val alpha = remember { mutableStateOf(true) }
                val colorPrimary = MaterialTheme.colorScheme.error
                val colorOnSurface = MaterialTheme.colorScheme.onSurface
                val color = remember { mutableStateOf(colorPrimary) }
                LaunchedEffect(alpha.value) {
                    delay(delayTime)
                    color.value = if (alpha.value) colorOnSurface else colorPrimary
                    alpha.value = !alpha.value
                }
                Icon(
                    imageVector = Icons.TwoTone.SignalWifiOff,
                    contentDescription = "navigationIconContentDescription",
                    tint = color.value,
                )
            }
        }
    }

    val actions: @Composable RowScope.() -> Unit = {
        if (onCallClick != null) {
            IconButton(onClick = onCallClick) {
                Icon(
                    imageVector = Icons.Sharp.Call,
                    contentDescription = "navigationIconContentDescription",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
        if (onSettingsActionClick != null) {
            IconButton(onClick = onSettingsActionClick) {
                Icon(
                    imageVector = Icons.Sharp.MoreVert,
                    contentDescription = "navigationIconContentDescription",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
//        if (onCallClick != null) {
//
//        }
        if (onSearchClick != null) {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Sharp.Search,
                    contentDescription = "navigationIconContentDescription",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }

        }
    }
    CenterAlignedTopAppBar(
        modifier = modifier,
        windowInsets = WindowInsets(0, 0, 0, 0),
        title = title,
        navigationIcon = navBackIcon,
        actions = actions,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}