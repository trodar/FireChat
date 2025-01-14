package com.trodar.utils.util

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import java.io.File


data class FileMetaData (
    var displayName: String? = null,
    var size: Long = 0,
    var mimeType: String? = null,
    var path: String? = null,
)

fun getFileMetaData(context: Context, uri: Uri): FileMetaData? {
    val fileMetaData = FileMetaData()

    if ("file".equals(uri.scheme, ignoreCase = true)) {
        val file = uri.path?.let { File(it) }
        fileMetaData.displayName = file?.name
        fileMetaData.size = file?.length() ?: 0
        fileMetaData.path = file?.path

        return fileMetaData
    } else {
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)
        fileMetaData.mimeType = contentResolver.getType(uri)

        try {
            if (cursor != null && cursor.moveToFirst()) {
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME).coerceAtLeast(0)
                fileMetaData.displayName = cursor.getString(index)

                if (!cursor.isNull(sizeIndex)) fileMetaData.size = cursor.getLong(sizeIndex)
                else fileMetaData.size = -1

                try {
                    fileMetaData.path = cursor.getString(cursor.getColumnIndexOrThrow("_data"))
                } catch (e: Exception) {
                    // DO NOTHING, _data does not exist
                }

                return fileMetaData
            } else {
                val file =uri.toFile()
                if (file.exists()) {
                    fileMetaData.displayName = file.name
                    return fileMetaData
                }

            }
        } catch (e: Exception) {
            Log.e("MAcK", e.toString())
        } finally {
            cursor?.close()
        }

        return null
    }
}