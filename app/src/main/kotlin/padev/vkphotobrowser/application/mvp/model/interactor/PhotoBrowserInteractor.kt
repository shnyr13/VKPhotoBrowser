package padev.vkphotobrowser.application.mvp.model.interactor

import android.graphics.Bitmap
import com.vk.sdk.api.*
import kotlinx.android.synthetic.main.activity_friends_friend_card.view.*
import org.json.JSONArray
import org.json.JSONObject
import padev.vkphotobrowser.application.mvp.model.entities.PhotoEntity
import padev.vkphotobrowser.application.mvp.presenter.photo_browser.IPhotoBrowserPresenter
import padev.vkphotobrowser.core.LoadImageAsyncTask
import padev.vkphotobrowser.core.LoadImageListener

class PhotoBrowserInteractor (val presenter: IPhotoBrowserPresenter) {

    fun loadPhoto(photoId: String) {

        val request = VKRequest("photos.getById", VKParameters.from(VKApiConst.PHOTOS, photoId, VKApiConst.EXTENDED, "1"))

        request.executeWithListener(object: VKRequest.VKRequestListener(), LoadImageListener {

            override fun onComplete(response: VKResponse) {
                super.onComplete(response)

                val jsonArray = response.json.getJSONArray("response")

                if (jsonArray.length() <= 0) return

                val jsonObject = jsonArray[0] as JSONObject

                var photoSize = "photo_2560"

                if (!jsonObject.has(photoSize)) {
                    photoSize = "photo_1280"
                }
                if (!jsonObject.has(photoSize)) {
                    photoSize = "photo_807"
                }
                if (!jsonObject.has(photoSize)) {
                    photoSize = "photo_604"
                }
                if (!jsonObject.has(photoSize)) {
                    photoSize = "photo_130"
                }
                if (!jsonObject.has(photoSize)) {
                    photoSize = "photo_75"
                }
                if (!jsonObject.has(photoSize)) {
                    presenter.showError("Photo do not found on VK server")
                    return
                }

                val photoUrl = jsonObject.getString(photoSize)

                var date = jsonObject.getString("date")
                val text = jsonObject.getString("text")
                val likes = jsonObject.getJSONObject("likes").getString("count")
                val comments = jsonObject.getJSONObject("comments").getString("count")

                presenter.showPhotoInfo(PhotoEntity(photoUrl, date, text, likes, comments))

                val liat = LoadImageAsyncTask()
                liat.listeners.add(this)
                liat.execute(photoUrl)
            }

            override fun imageLoadFinish(id: String, bitmap: Bitmap) {
                presenter.showPhoto(bitmap)
            }

            override fun onError(error: VKError?) {
                super.onError(error)

                presenter.showError("Photo do not found on VK server")
            }
        })
    }
}