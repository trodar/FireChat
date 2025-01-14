package com.trodar.data.di

import com.trodar.data.repository.AccountRepository
import com.trodar.data.repository.LocalAccountRepository
import com.trodar.data.repository.LocalMessageRepository
import com.trodar.data.repository.LocalSettingsRepository
import com.trodar.data.repository.MessageRepository
import com.trodar.data.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsContactRepository(
        localContactRepository: LocalAccountRepository
    ): AccountRepository

    @Binds
    abstract fun bindsChatRepository(
        localChatRepository: LocalMessageRepository
    ): MessageRepository

    @Binds
    abstract fun bindsSettingsDataRepository(
        localSettingsRepository: LocalSettingsRepository
    ): SettingsRepository

}