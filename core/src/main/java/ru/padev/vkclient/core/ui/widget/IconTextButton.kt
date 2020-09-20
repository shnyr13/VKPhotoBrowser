package ru.padev.vkclient.core.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import kotlinx.android.synthetic.main.layout_button_with_icon_text.view.*
import ru.padev.vkclient.core.R

class IconTextButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_button_with_icon_text, this, true)
    }

    fun setBase(baseText: String, @DrawableRes iconRes: Int) {
        icon_text_button_text.text = baseText
        icon_text_button_icon.setImageResource(iconRes)
    }

    fun setImageResource(@DrawableRes iconRes: Int) {
        icon_text_button_icon.setImageResource(iconRes)
    }

    fun setTextColor(color: Int) {
        icon_text_button_text.setTextColor(color)
    }
}