package com.trodar.utils.extension

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

fun Uri.getImageExtension(context: Context): String {
    var res: String? = null
    val proj = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
    val cursor: Cursor? = context.contentResolver.query(this, proj, null, null, null)
    if (cursor?.moveToFirst() == true) {
        val columnIndex =
            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
        res = cursor.getString(columnIndex)
    }
    cursor?.close()

    return res?.substringAfterLast(".", "jpg") ?: "jpg"
}