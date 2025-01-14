package com.trodar.ui

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat


fun checkPermission(
    permission: String,
    context: Context,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>
): Boolean {
    if (ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        return true
    } else {
        permissionLauncher.launch(permission)
        return false
    }
}