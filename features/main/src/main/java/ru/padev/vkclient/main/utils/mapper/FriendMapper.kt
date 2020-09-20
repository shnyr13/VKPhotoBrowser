package ru.padev.vkclient.main.utils.mapper

import ru.padev.vkclient.main.data.response.Friend
import ru.padev.vkclient.main.data.response.UserDetails
import ru.padev.vkclient.main.data.vo.FriendDetailsVO
import ru.padev.vkclient.main.data.vo.FriendVO

object FriendMapper: BaseMapper<FriendVO, Friend>() {
    override fun transform(item: Friend): FriendVO
            = FriendVO (
                id = item.id,
                firstName = item.firstName,
                lastName = item.lastName,
                photo100 = item.photo100 ?: "",
                photoId = item.photoId ?: "",
                online = item.online == 1
            )

    fun transform(item: UserDetails): FriendDetailsVO
            = FriendDetailsVO (
                id = item.id,
                firstName = item.firstName,
                lastName = item.lastName,
                photoMax = item.photoMax ?: "",
                photoId = item.photoId ?: "",
                city = item.city?.title ?: "",
                country = item.country?.title ?: "",
                online = item.online == 1
            )
}