package padev.vkphotobrowser.application.mvp.presenter.friends

import android.graphics.Bitmap
import padev.vkphotobrowser.application.mvp.model.entities.FriendEntity
import padev.vkphotobrowser.application.mvp.model.interactor.FriendsInteractor
import padev.vkphotobrowser.application.mvp.view.IFriendsView

class FriendsPresenter(val viewState: IFriendsView): IFriendsPresenter {

    val interactor = FriendsInteractor(this)

    override fun loadFriends() {
        interactor.loadFriends()
    }

    override fun showFriend(friend: FriendEntity) {
        viewState.showFriend(friend)
    }

    override fun showProfilePhoto(photoId: String, bitmap: Bitmap) {
        viewState.showFriendPhoto(photoId, bitmap)
    }
}