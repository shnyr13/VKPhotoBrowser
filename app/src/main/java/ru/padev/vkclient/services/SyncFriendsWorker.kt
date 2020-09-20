package ru.padev.vkclient.services

import android.content.Context
import androidx.room.Database
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Single
import ru.padev.vkclient.main.interactor.FriendsSyncInteractor
import ru.padev.vkclient.VkClientApp
import ru.padev.vkclient.main.interactor.FriendsInteractor
import timber.log.Timber
import javax.inject.Inject

class SyncFriendsWorker (
    context: Context,
    workerParameters: WorkerParameters,
) : RxWorker(context, workerParameters) {

    @Inject
    lateinit var mFriendsInteractor: FriendsInteractor

    override fun createWork(): Single<Result> {
        (applicationContext as VkClientApp).appComponent.inject(this)

        return mFriendsInteractor.getFriends()
            .doOnError { throwable: Throwable ->
                Timber.e(
                    throwable,
                    "Error sync friends"
                )
            }
            .doOnSuccess {
                mFriendsInteractor.saveFriendsIntoCache(it)
            }
            .map { Result.success() }
    }
}