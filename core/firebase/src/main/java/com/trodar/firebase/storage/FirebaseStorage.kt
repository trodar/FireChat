package com.trodar.firebase.storage

import android.net.Uri
import com.google.firebase.storage.StorageReference
import com.trodar.firebase.StorageDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireStorage @Inject constructor(
    private val storage: StorageReference
): StorageDataSource {
    override suspend fun upsertPhoto(localPath: Uri, remotePath: String): Flow<Uri> = flow {
        val ref = storage.child(remotePath)
        ref.putFile(localPath).await()

        val uri = ref.downloadUrl.await()

        emit(uri)
    }
}