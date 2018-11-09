package padev.vkphotobrowser.core

import android.graphics.Bitmap

interface LoadImageListener {
    fun imageLoadFinish(id: String, bitmap: Bitmap)
}