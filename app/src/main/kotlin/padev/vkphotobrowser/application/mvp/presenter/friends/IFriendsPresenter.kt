package padev.vkphotobrowser.application.mvp.presenter.friends

import android.graphics.Bitmap
import padev.vkphotobrowser.application.mvp.model.entities.FriendEntity

interface IFriendsPresenter {

    fun loadFriends()

    fun showFriend(friend: FriendEntity)

    fun showProfilePhoto(id: String, bitmap: Bitmap)

}