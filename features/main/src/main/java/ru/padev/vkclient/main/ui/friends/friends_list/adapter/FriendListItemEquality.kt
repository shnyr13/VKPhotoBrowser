package ru.padev.vkclient.main.ui.friends.friends_list.adapter

import ru.padev.vkclient.core.ui.difflist.internal.EqualityFunction
import ru.padev.vkclient.core.ui.difflist.adapter.Equality
import ru.padev.vkclient.main.data.vo.FriendVO

object FriendListItemEquality: Equality<FriendVO> {
    override val identityEquality: EqualityFunction<in FriendVO>
        get() = object : EqualityFunction<FriendVO> {
            override fun equal(a: FriendVO, b: FriendVO): Boolean
                = a.javaClass == b.javaClass && a.id == b.id
        }

    override val contentEquality: EqualityFunction<in FriendVO>
        get() = object : EqualityFunction<FriendVO> {
            override fun equal(a: FriendVO, b: FriendVO): Boolean
                = a.javaClass == b.javaClass && a == b
        }
}