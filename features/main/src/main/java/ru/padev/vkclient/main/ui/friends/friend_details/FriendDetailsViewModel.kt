package ru.padev.vkclient.main.ui.friends.friend_details

import androidx.lifecycle.MutableLiveData
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.padev.vkclient.core.presentation.BaseViewModel
import ru.padev.vkclient.main.data.vo.FriendDetailsVO
import ru.padev.vkclient.main.data.vo.FriendVO
import ru.padev.vkclient.main.interactor.FriendsInteractor
import java.util.*
import kotlin.collections.ArrayList

class FriendDetailsViewModel @AssistedInject constructor(
    @Assisted private val mFriendVO: FriendVO,
    private val mFriendsInteractor: FriendsInteractor
): BaseViewModel() {

    val friendDetails: MutableLiveData<FriendDetailsVO> by lazy {
        MutableLiveData<FriendDetailsVO>().apply {
            postValue(
                FriendDetailsVO (
                    id = mFriendVO.id,
                    firstName = mFriendVO.firstName,
                    lastName = mFriendVO.lastName,
                    photoMax = mFriendVO.photo100,
                    photoId = mFriendVO.photoId,
                    city = "",
                    country = "",
                    online = mFriendVO.online
                )
            )
        }
    }

    val loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun start() {
        mFriendsInteractor.getFriendById(mFriendVO.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.postValue(true) }
            .doAfterTerminate { loading.postValue(false)}
            .subscribe (
                {
                    friendDetails.postValue(it)
                },
                {

                }
            ).addToDisposables()
    }

    @AssistedInject.Factory
    interface Factory {
        fun get(mFriendVO: FriendVO): FriendDetailsViewModel
    }
}