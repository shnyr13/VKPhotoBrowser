package ru.padev.vkclient.core.database.dao

import androidx.room.Dao
import ru.padev.vkclient.core.database.entity.FriendEntity

@Dao
abstract class FriendDao : BaseDao<FriendEntity>