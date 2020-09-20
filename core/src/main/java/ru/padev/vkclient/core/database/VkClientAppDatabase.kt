package ru.padev.vkclient.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.padev.vkclient.core.database.dao.*
import ru.padev.vkclient.core.database.entity.*

@Database(
    entities = [
        FriendEntity::class
    ],
    version = DbConstants.DATABASE_VERSION
)
abstract class VkClientAppDatabase : RoomDatabase() {
    abstract val friendDao: FriendDao
}