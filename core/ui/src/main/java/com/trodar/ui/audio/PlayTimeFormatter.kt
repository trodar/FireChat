package com.trodar.ui.audio

import java.util.concurrent.TimeUnit

internal class PlayTimeFormatter {
    fun format(playbackTimeSeconds: Long): String {
        val minutes = TimeUnit.SECONDS.toMinutes(playbackTimeSeconds)
        val seconds = if (playbackTimeSeconds < 60) {
            playbackTimeSeconds
        } else {
            (playbackTimeSeconds - TimeUnit.MINUTES.toSeconds(minutes))
        }
        return buildString {
            if (minutes < 10) append(0)
            append(minutes)
            append(":")
            if (seconds < 10) append(0)
            append(seconds)
        }
    }
}