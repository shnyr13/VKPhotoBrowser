package padev.vkphotobrowser.application.mvp.presenter.photo_browser

import android.graphics.Bitmap
import padev.vkphotobrowser.application.mvp.model.entities.PhotoEntity

interface IPhotoBrowserPresenter {

    fun showPhotoInfo(photo: PhotoEntity)

    fun showPhoto(bitmap: Bitmap)

    fun loadPhoto(photoId: String)

    fun showError(error: String)
}