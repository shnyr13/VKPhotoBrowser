package padev.vkphotobrowser.application.mvp.model.interactor

import android.graphics.Bitmap
import com.vk.sdk.api.*
import org.json.JSONObject
import padev.vkphotobrowser.application.mvp.model.entities.FriendEntity
import padev.vkphotobrowser.application.mvp.presenter.friends.IFriendsPresenter
import padev.vkphotobrowser.core.LoadImageAsyncTask
import padev.vkphotobrowser.core.LoadImageListener

class FriendsInteractor(val presenter: IFriendsPresenter) {

    fun loadFriends() {

        val request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "first_name, last_name, photo_100, photo_id"))
        request.executeWithListener(object: VKRequest.VKRequestListener(), LoadImageListener {

            override fun onComplete(response: VKResponse) {
                super.onComplete(response)

                val jsonArr = response.json.getJSONObject("response").getJSONArray("items")
                var i = 0
                while (i < jsonArr.length()) {

                    val jsonObject = jsonArr[i] as JSONObject
                    val firstName = jsonObject.getString("first_name")
                    val lastName = jsonObject.getString("last_name")
                    val id = jsonObject.getString("id")
                    val photoUrl = jsonObject.getString("photo_100")
                    var photoId = ""
                    if (jsonObject.has("photo_id")) {
                        photoId = jsonObject.getString("photo_id")
                    }

                    presenter.showFriend(FriendEntity(id, firstName, lastName, photoUrl, photoId))

                    val liat = LoadImageAsyncTask()
                    liat.listeners.add(this)
                    liat.execute(photoUrl, photoId)

                    i++
                }
            }

            override fun imageLoadFinish(photoId: String, bitmap: Bitmap) {
                presenter.showProfilePhoto(photoId, bitmap)
            }

            override fun onError(error: VKError?) {
                super.onError(error)
            }
        })
    }
}