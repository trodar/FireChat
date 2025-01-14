package com.trodar.firebase

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface StorageDataSource {
    suspend fun upsertPhoto(localPath: Uri, remotePath: String): Flow<Uri>
}