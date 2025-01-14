package com.trodar.domain.settings

import android.content.Context
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.trodar.firebase.StorageDataSource
import com.trodar.utils.extension.getImageExtension
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateLogoUseCase @Inject constructor(
    private val auth: FirebaseAuth,
    private val storageDataSource: StorageDataSource,
    @ApplicationContext private val context: Context,
) {

    private val userPhotoPath = "user/%s/image/profile.%s"

    suspend operator fun invoke(
        logoUri: Uri
    ): Flow<Uri> {
        val remotePath = String.format(
            userPhotoPath,
            auth.currentUser?.uid,
            logoUri.getImageExtension(context = context)
        )
        return storageDataSource.upsertPhoto(logoUri, remotePath)
     }
}