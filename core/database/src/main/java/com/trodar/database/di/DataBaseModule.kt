package com.trodar.database.di

import android.content.Context
import androidx.room.Room
import com.trodar.database.FireChatDataBase
import com.trodar.database.dao.MessageDao
import com.trodar.database.dao.AccountDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun providesFireChatDatabase(
        @ApplicationContext context: Context
    ): FireChatDataBase = Room.databaseBuilder(
        context,
        FireChatDataBase::class.java,
        "firechat-database",
    ).build()

    @Provides
    fun providesAccountDao(
        dataBase: FireChatDataBase
    ): AccountDao = dataBase.AccountDao()

    @Provides
    fun providesChatDao(
        dataBase: FireChatDataBase
    ): MessageDao = dataBase.MessageDao()
}