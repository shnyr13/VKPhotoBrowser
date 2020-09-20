package ru.padev.vkclient.main.repository

import com.google.gson.Gson
import com.vk.sdk.api.*
import io.reactivex.Single
import ru.padev.vkclient.core.database.VkClientAppDatabase
import ru.padev.vkclient.core.database.entity.FriendEntity
import ru.padev.vkclient.main.data.response.Friend
import ru.padev.vkclient.main.data.response.FriendsResponse
import ru.padev.vkclient.main.data.response.UserDetails
import ru.padev.vkclient.main.data.response.UserDetailsResponse
import ru.padev.vkclient.main.data.vo.FriendVO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendsRepository @Inject constructor(
    private val mGson: Gson,
    private val mDataBase: VkClientAppDatabase
) {
    fun getFriends(): Single<List<Friend>> {
        val request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "first_name, last_name, photo_100, photo_id"))

        return Single.create<VKResponse> { subscriber ->
            request.executeWithListener(object : VKRequest.VKRequestListener() {

                override fun attemptFailed(
                    request: VKRequest,
                    attemptNumber: Int,
                    totalAttempts: Int
                ) {
                    super.attemptFailed(request, attemptNumber, totalAttempts)
                    subscriber.onError(Exception())
                }

                override fun onComplete(response: VKResponse) {
                    super.onComplete(response)

                    subscriber.onSuccess(response)
                }

                override fun onError(error: VKError) {
                    super.onError(error)

                    subscriber.onError(Exception(error.errorMessage))
                }
            })
        }
            .map { it.json }
            .map { mGson.fromJson(it.toString(), FriendsResponse::class.java).friendsList.friends }
    }

    fun getFriendById(id: Int): Single<UserDetails> {
        val request = VKApi.users().get(
            VKParameters.from(
            VKApiConst.USER_ID, id.toString(),
            VKApiConst.FIELDS, "first_name, last_name, photo_max, photo_id, city, country, online"
        )
        )

        return Single.create<VKResponse> { subscriber ->
            request.executeWithListener(object : VKRequest.VKRequestListener() {

                override fun attemptFailed(
                    request: VKRequest,
                    attemptNumber: Int,
                    totalAttempts: Int
                ) {
                    super.attemptFailed(request, attemptNumber, totalAttempts)
                    subscriber.onError(Exception())
                }

                override fun onComplete(response: VKResponse) {
                    super.onComplete(response)

                    subscriber.onSuccess(response)
                }

                override fun onError(error: VKError) {
                    super.onError(error)

                    subscriber.onError(Exception(error.errorMessage))
                }
            })
        }
            .map { it.json }
            .map { mGson.fromJson(it.toString(), UserDetailsResponse::class.java).users[0] }
    }

    fun saveFriendsIntoCache(entities: List<FriendEntity>) {
        mDataBase.friendDao.insert(entities)
    }
}