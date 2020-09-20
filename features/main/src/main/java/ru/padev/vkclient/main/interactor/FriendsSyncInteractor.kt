package ru.padev.vkclient.main.interactor

import ru.padev.vkclient.main.repository.FriendsRepository
import ru.padev.vkclient.main.repository.FriendsSyncRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendsSyncInteractor @Inject constructor(
    private val mFriendsSyncRepository: FriendsSyncRepository,
    private val mFriendsRepository: FriendsRepository
) {

}