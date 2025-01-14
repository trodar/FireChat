package com.trodar.media

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class AndroidAudioRecorder @Inject constructor(
    @ApplicationContext private val context: Context
) : AudioRecorder {
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var filePath: File? = null

    override suspend fun startRecord(outputFile: File) {
        filePath = outputFile
        recorder = createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.DEFAULT)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(FileOutputStream(outputFile).fd)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

            try {
                prepare()

            } catch (e: Exception) {
                Log.d("MAcK", e.toString())
            }
            start()
        }
    }

    override suspend fun stopRecord() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

    override suspend fun startPlay(filePath: String): Flow<Boolean> = channelFlow {
        player = MediaPlayer().apply {
            setDataSource(filePath)
            try {
                prepare()

            } catch (e: Exception) {
                Log.d("MAcK", e.toString())
            }
            start()

        }
        player?.setOnCompletionListener {
            this.trySendBlocking(true)
        }
        awaitClose()
    }

    override suspend fun stopPlay() {
        player?.release()
        player = null
    }

    override fun getFile(): File? {
        return filePath
    }

    @Suppress("DEPRECATION")
    private fun createRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else MediaRecorder()
    }


}