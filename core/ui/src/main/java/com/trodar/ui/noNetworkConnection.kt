package com.trodar.ui

import android.content.Context

fun noNetworkConnection(
    isOffline: Boolean,
    context: Context,
    onError: (String) -> Unit,
    content: () -> Unit,
){
    if (isOffline) {
        onError(context.getString(R.string.core_ui_no_internet))
        return
    }
    content()
}