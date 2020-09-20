package ru.padev.vkclient.main.repository

import ru.padev.vkclient.core.database.VkClientAppDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendsSyncRepository @Inject constructor(private val mDataBase: VkClientAppDatabase) {

}