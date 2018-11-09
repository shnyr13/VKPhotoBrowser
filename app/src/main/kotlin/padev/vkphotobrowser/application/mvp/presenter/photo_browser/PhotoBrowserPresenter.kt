package padev.vkphotobrowser.application.mvp.presenter.photo_browser

import android.graphics.Bitmap
import padev.vkphotobrowser.application.mvp.model.entities.PhotoEntity
import padev.vkphotobrowser.application.mvp.model.interactor.PhotoBrowserInteractor
import padev.vkphotobrowser.application.mvp.view.IPhotoBrowserView

class PhotoBrowserPresenter (val viewState: IPhotoBrowserView): IPhotoBrowserPresenter  {

    val interactor = PhotoBrowserInteractor(this)

    override fun showPhotoInfo(photo: PhotoEntity) {
        viewState.showPhotoInfo(photo)
    }

    override fun showPhoto(bitmap: Bitmap) {
        viewState.showProfilePhoto(bitmap)
    }

    override fun loadPhoto(photoId: String) {
        interactor.loadPhoto(photoId)
    }

    override fun showError(error: String) {
        viewState.showError(error)
    }
}