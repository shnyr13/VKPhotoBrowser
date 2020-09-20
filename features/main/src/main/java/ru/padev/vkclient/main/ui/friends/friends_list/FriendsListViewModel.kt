package ru.padev.vkclient.main.ui.friends.friends_list

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import ru.padev.vkclient.core.presentation.BaseViewModel
import ru.padev.vkclient.main.R
import ru.padev.vkclient.main.data.vo.FriendVO
import ru.padev.vkclient.main.interactor.FriendsInteractor
import ru.padev.vkclient.main.ui.NavigationEvent
import ru.padev.vkclient.main.utils.MainFeatureConstants
import javax.inject.Inject

class FriendsListViewModel @Inject constructor (
    private val mFriendsInteractor: FriendsInteractor
): BaseViewModel() {
    val friends: MutableLiveData<List<FriendVO>> by lazy {
        MutableLiveData<List<FriendVO>>()
    }

    val loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val friendDidClickSubject = PublishSubject.create<FriendVO>().apply {
        subscribe {
            eventLiveData.postEvent (
                NavigationEvent (
                    R.id.navigate_to_friend_details,

                    Bundle().apply {
                        putSerializable (
                            MainFeatureConstants.ExtraData.Friend.DATA,
                            it
                        )
                    }
                )
            )
        }.addToDisposables()
    }

    fun refreshFriends() {
        mFriendsInteractor.getFriends()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.postValue(true) }
            .doAfterTerminate { loading.postValue(false) }
            .subscribe (
                {
                    friends.postValue (
                        it.sortedBy { friend -> !friend.online }
                    )
                },
                {

                }
            ).addToDisposables()
    }
}