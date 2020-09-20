package ru.padev.vkclient.core.ui

import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import kotlin.math.min

abstract class BaseDialogFragment : DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (getScreenWidth() * 0.9).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun getScreenWidth(): Int {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R){
            min(
                requireActivity().windowManager.maximumWindowMetrics.bounds.width(),
                requireActivity().windowManager.maximumWindowMetrics.bounds.height()
            )
        } else {
            val metrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
            min(metrics.widthPixels, metrics.heightPixels)
        }
    }
}