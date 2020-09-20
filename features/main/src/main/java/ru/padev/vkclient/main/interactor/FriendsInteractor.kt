package ru.padev.vkclient.main.interactor

import io.reactivex.Single
import ru.padev.vkclient.core.database.entity.FriendEntity
import ru.padev.vkclient.main.data.vo.FriendDetailsVO
import ru.padev.vkclient.main.data.vo.FriendVO
import ru.padev.vkclient.main.repository.FriendsRepository
import ru.padev.vkclient.main.utils.mapper.FriendMapper
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FriendsInteractor @Inject constructor(
    private val mFriendsRepository: FriendsRepository
) {
    fun getFriends(): Single<List<FriendVO>> {
        return mFriendsRepository.getFriends()
        .map{ FriendMapper.transform(it) }
    }

    fun getFriendById(id: Int): Single<FriendDetailsVO> {
        return mFriendsRepository.getFriendById(id)
            .map{ FriendMapper.transform(it) }
    }

    fun saveFriendsIntoCache(friends: List<FriendVO>) {
        val entities = ArrayList<FriendEntity>(friends.size)

        friends.forEach {
            entities.add (
                FriendEntity (
                    it.id.toLong(),
                    it.firstName,
                    it.lastName,
                    it.photo100
                )
            )
        }

        mFriendsRepository.saveFriendsIntoCache(entities)
    }
}