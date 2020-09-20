package ru.padev.vkclient.core.database

object DbConstants {

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "vkclient-app-database"

    object Friend {
        const val TABLE_NAME = "friend"
        const val PREFIX = "crw_friend_"
        const val ID = PREFIX + "id"
        const val FIRST_NAME = PREFIX + "first_name"
        const val LAST_NAME = PREFIX + "last_name"
        const val IMAGE_URL = PREFIX + "imageUrl"
    }
}