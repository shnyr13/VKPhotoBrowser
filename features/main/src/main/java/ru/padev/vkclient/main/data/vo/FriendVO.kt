package ru.padev.vkclient.main.data.vo

import java.io.Serializable

data class FriendVO (
    val id: Int,
    val firstName: String,
    val lastName: String,
    val photo100: String,
    val photoId: String,
    val online: Boolean
): Serializable {
    companion object Default {
        val defaultValue
            get() = FriendVO (
                id = 0,
                firstName = "",
                lastName = "",
                photo100 = "",
                photoId = "",
                online = false
            )
    }

    val fullName = "$firstName $lastName"
}