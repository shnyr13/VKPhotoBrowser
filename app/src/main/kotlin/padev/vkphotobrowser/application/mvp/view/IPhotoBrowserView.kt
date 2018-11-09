package padev.vkphotobrowser.application.mvp.view

import android.graphics.Bitmap
import padev.vkphotobrowser.application.mvp.model.entities.PhotoEntity

interface IPhotoBrowserView {

    fun startLoading()

    fun endLoading()

    fun showError(errorText: String)

    fun showPhotoInfo(photo: PhotoEntity)

    fun showProfilePhoto(bitmap: Bitmap)
}