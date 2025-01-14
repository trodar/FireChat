package com.trodar.media.di

import com.trodar.media.AndroidAudioRecorder
import com.trodar.media.AudioRecorder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class AudioDataModule {

    @Binds
    internal abstract fun bindsAudioRecorder(
        networkMonitor: AndroidAudioRecorder,
    ): AudioRecorder
}
