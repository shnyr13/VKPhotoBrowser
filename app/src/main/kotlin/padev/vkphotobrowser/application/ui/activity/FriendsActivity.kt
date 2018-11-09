package padev.vkphotobrowser.application.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import padev.vkphotobrowser.R
import padev.vkphotobrowser.application.mvp.model.entities.FriendEntity
import padev.vkphotobrowser.application.mvp.presenter.friends.FriendsPresenter
import padev.vkphotobrowser.application.mvp.presenter.friends.IFriendsPresenter
import padev.vkphotobrowser.application.mvp.view.IFriendsView
import padev.vkphotobrowser.core.view.BaseActivity

class FriendsActivity: BaseActivity(), IFriendsView {

    private val TAG: String = FriendsActivity::class.java.simpleName

    private val presenter: IFriendsPresenter = FriendsPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_friends)

        presenter.loadFriends()
    }

    override fun showFriend(friend: FriendEntity) {
        val friendCardLayout = layoutInflater.inflate(
            R.layout.activity_friends_friend_card,
            findViewById<LinearLayout>(R.id.activity_friends_friends_list),
            true
        ) as LinearLayout

        val currentView = friendCardLayout.getChildAt(friendCardLayout.childCount - 1)

        val friendNameTextView = currentView.findViewById<TextView>(R.id.activity_friends_friend_card_name)

        val nameStr = friend.first_name.plus(" ").plus(friend.last_name)
        friendNameTextView.text = nameStr

        val profilePhoto = currentView.findViewById<ImageView>(R.id.activity_friends_friend_card_photo)
        profilePhoto.tag = friend.photo_id

        profilePhoto.setOnClickListener {
            val intent = Intent(applicationContext, PhotoBrowserActivity::class.java)
            intent.putExtra("photo_id", it.tag as String)
            startActivity(intent)
        }
    }

    override fun showFriendPhoto(photoId: String, bitmap: Bitmap) {
        val profilePhoto = findViewById<View>(R.id.activity_friends_friends_list).findViewWithTag<ImageView>(photoId)
        profilePhoto.setImageBitmap(bitmap)
    }



    override fun startLoading() {

    }

    override fun endLoading() {

    }

    override fun showError(errorText: String) {
        Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT).show()
    }

}
