package com.trodar.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter


@Composable
fun ProfileImage(
    imageSize: Dp = 64.dp,
    editing: Boolean = false,
    uri: Uri? = null,
    onClick: () -> Unit,
) {
    val modifier = Modifier
        .size(imageSize)
        .clip(CircleShape)
        .border(2.dp, Color.LightGray, CircleShape)
        .let {
            if (editing)
                it.clickable {  onClick() }
            else
                it
        }

    if (uri == null) {

        Image(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "add contact",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = modifier
        )
        return
    }

    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val imagePainter = rememberAsyncImagePainter(
        model = uri,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = true
        },
    )
    val isLocalInspection = LocalInspectionMode.current
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading && !isLocalInspection) {
            // Display a progress bar while loading
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(imageSize - 12.dp),
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
        Image(
            imagePainter,
            contentDescription = "ss",
            contentScale = ContentScale.Crop,
            modifier = modifier

        )
    }
}