package padev.vkphotobrowser.application.mvp.view

import android.graphics.Bitmap
import padev.vkphotobrowser.application.mvp.model.entities.FriendEntity

interface IFriendsView {

    fun startLoading()

    fun endLoading()

    fun showError(errorText: String)

    fun showFriend(friend: FriendEntity)

    fun showFriendPhoto(id: String, bitmap: Bitmap)
}