package ru.padev.vkclient.main.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.util.concurrent.atomic.AtomicInteger

object AndroidUtils {
    private val SEED = AtomicInteger()
    private const val ENABLE_ALPHA_VALUE = 255
    private const val DISABLE_ALPHA_VALUE = 130
    fun getBitmap(context: Context, @DrawableRes drawableResId: Int): Bitmap {
        val drawable = context.getDrawable(drawableResId)
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else {
            getBitmap(drawable!!)
        }
    }

    private fun getBitmap(layerDrawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            layerDrawable.intrinsicWidth,
            layerDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        layerDrawable.setBounds(0, 0, canvas.width, canvas.height)
        layerDrawable.draw(canvas)
        return bitmap
    }

    fun bitmapDescriptorFromVector(context: Context, @DrawableRes vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}