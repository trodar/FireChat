package com.trodar.ui.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Camera
import androidx.compose.material.icons.sharp.Photo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.trodar.ui.R
import java.io.File


@Composable
fun SelectPhotoBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onSuccess: (Uri) -> Unit,
) {
    val context = LocalContext.current
    val authority = stringResource(id = R.string.core_ui_file_provider)
    val tempUri = remember { mutableStateOf<Uri?>(null) }
    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {
            tempUri.value?.let {
                onSuccess(it)
            }
        }
    )
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted, proceed to step 2
            val tmpUri = getTempUri(context, authority)
            tempUri.value = tmpUri
            tempUri.value?.let {
                takePhotoLauncher.launch(it)
            }

        } else {
            onDismiss()
        }
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let {
                onSuccess(it)
            }
        }
    )

    ChatModalBottomSheetContent(
        modifier = modifier,
        items = listOf(
            BottomSheetItem(
                title = "Take Photo",
                icon = Icons.Sharp.Camera,
                onClick = {
                    val permission = Manifest.permission.CAMERA
                    if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
                    ) {
                        // Permission is already granted, proceed to step 2
                        val tmpUri = getTempUri(context, authority)
                        tempUri.value = tmpUri
                        tempUri.value?.let {
                            takePhotoLauncher.launch(it)
                        }

                    } else {
                        // Permission is not granted, request it
                        cameraPermissionLauncher.launch(permission)
                    }
                },
            ),
            BottomSheetItem(
                title = "Select Image",
                icon = Icons.Sharp.Photo,
                onClick = {
                    imagePicker.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
            ),
        ),
        onDismiss = onDismiss,
    )
}

fun getTempUri(context: Context, authority: String): Uri? {
    val directory = File(context.cacheDir, "images")
    directory.mkdirs()
    directory.let {
        it.mkdirs()
        val file = File.createTempFile(
            "image_" + System.currentTimeMillis().toString(),
            ".jpg",
            it
        )

        return FileProvider.getUriForFile(
            context,
            authority,
            file
        )
    }
}