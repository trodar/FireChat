package com.trodar.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.trodar.database.dao.AccountDao
import com.trodar.database.dao.MessageDao
import com.trodar.database.model.AccountEntity
import com.trodar.database.model.MessageEntity


@Database(
    entities = [
        AccountEntity::class,
        MessageEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class FireChatDataBase : RoomDatabase() {

    abstract fun AccountDao(): AccountDao

    abstract fun MessageDao(): MessageDao
}