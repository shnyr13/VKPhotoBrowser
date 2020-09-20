package ru.padev.vkclient.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.padev.vkclient.core.database.DbConstants

@Entity(tableName = DbConstants.Friend.TABLE_NAME)
data class FriendEntity(

    @ColumnInfo(name = DbConstants.Friend.ID)
    @PrimaryKey
    var id: Long,

    @ColumnInfo(name = DbConstants.Friend.FIRST_NAME)
    var firstName: String,

    @ColumnInfo(name = DbConstants.Friend.LAST_NAME)
    var lastName: String,

    @ColumnInfo(name = DbConstants.Friend.IMAGE_URL)
    var photoUrl: String
)