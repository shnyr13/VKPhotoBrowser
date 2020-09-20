package ru.padev.vkclient.main.data.vo

data class FriendDetailsVO (
    val id: Int,
    val firstName: String,
    val lastName: String,
    val photoMax: String,
    val photoId: String,
    val city: String,
    val country: String,
    val online: Boolean
) {
    val fullName = "$firstName $lastName"
}