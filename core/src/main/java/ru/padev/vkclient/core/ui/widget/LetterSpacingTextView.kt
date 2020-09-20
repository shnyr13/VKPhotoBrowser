package ru.padev.vkclient.core.ui.widget

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ScaleXSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

import ru.padev.vkclient.core.R

class LetterSpacingTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatTextView(context, attrs, defStyle) {

    private var scaleFactor = 0f
    private var originalText: CharSequence? = null

    init {

        val originalTypedArray = context.obtainStyledAttributes(attrs, intArrayOf(android.R.attr.text))
        val currentTypedArray = context.obtainStyledAttributes(attrs, R.styleable.LetterSpacingTextView, 0, defStyle)

        try {
            scaleFactor = currentTypedArray.getFloat(R.styleable.LetterSpacingTextView_lstv_spacing, 0f)
            originalText = originalTypedArray.getText(0)
        } finally {
            originalTypedArray.recycle()
            currentTypedArray.recycle()
        }
        applyScaleFactor()
    }

    override fun setText(text: CharSequence, type: BufferType?) {
        originalText = text
        applyScaleFactor()
    }

    override fun getText(): CharSequence? {
        return originalText
    }

    /**
     * Programmatically get the value of the `scaleFactor`
     */
    fun getScaleFactor(): Float {
        return scaleFactor
    }

    /**
     * Programmatically set the value of the `scaleFactor`
     */
    fun setScaleFactor(scaleFactor: Float) {
        this.scaleFactor = scaleFactor
        applyScaleFactor()
    }

    private fun applyScaleFactor() {
        originalText?.let {text ->
            val builder = StringBuilder()
            for (i in text.indices) {
                builder.append(text[i])
                if (i + 1 < text.length) {
                    builder.append("\u00A0")
                }
            }
            val finalText = SpannableString(builder.toString())
            if (builder.toString().length > 1) {
                var i = 1
                while (i < builder.toString().length) {
                    finalText.setSpan(
                        ScaleXSpan(scaleFactor / 10), i, i + 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    i += 2
                }
            }
            super.setText(finalText, BufferType.SPANNABLE)
        }
    }
}