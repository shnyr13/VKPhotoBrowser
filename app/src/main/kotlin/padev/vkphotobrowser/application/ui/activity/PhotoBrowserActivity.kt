package padev.vkphotobrowser.application.ui.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import padev.vkphotobrowser.R
import padev.vkphotobrowser.application.mvp.model.entities.PhotoEntity
import padev.vkphotobrowser.application.mvp.presenter.photo_browser.IPhotoBrowserPresenter
import padev.vkphotobrowser.application.mvp.presenter.photo_browser.PhotoBrowserPresenter
import padev.vkphotobrowser.application.mvp.view.IPhotoBrowserView
import padev.vkphotobrowser.core.view.BaseActivity

class PhotoBrowserActivity: BaseActivity(), IPhotoBrowserView {

    private val TAG: String = PhotoBrowserActivity::class.java.simpleName

    private val presenter: IPhotoBrowserPresenter = PhotoBrowserPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_photo_browser)

        val photoId = intent.getStringExtra("photo_id")

        presenter.loadPhoto(photoId)
    }

    override fun startLoading() {

    }

    override fun endLoading() {

    }

    override fun showError(errorText: String) {
        Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT).show()
    }

    override fun showPhotoInfo(photo: PhotoEntity) {
        Toast.makeText(applicationContext,
            (photo.text
                .plus(" "))
                .plus("Likes: ".plus(photo.likes))
            , Toast.LENGTH_SHORT).show()
    }

    override fun showProfilePhoto(bitmap: Bitmap) {
        val photoImageView = findViewById<ImageView>(R.id.activity_photo_image_view)
        photoImageView.setImageBitmap(bitmap)
    }


}