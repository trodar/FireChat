package com.trodar.media

import kotlinx.coroutines.flow.Flow
import java.io.File

interface AudioRecorder {

    suspend fun startRecord(outputFile: File)
    suspend fun stopRecord()

    suspend fun startPlay(filePath: String): Flow<Boolean>
    suspend fun  stopPlay()

    fun getFile(): File?

}