package ru.padev.vkclient.core.domain.exception

import android.content.Context
import com.google.gson.Gson
import okhttp3.ResponseBody
import ru.padev.vkclient.core.R
import timber.log.Timber
import java.io.IOException
import java.lang.Exception

abstract class BaseErrorHandler(
    protected val gson: Gson,
    protected val context: Context
) {

    abstract fun getErrorMessage(exception: Exception, type: ErrorScope): String
    abstract fun getErrorCode(exception: Exception, type: ErrorScope): ErrorCode


    protected fun readResponseBody(responseBody: ResponseBody): String? {
        var content: String? = ""
        try {
            content = responseBody.string()
        } catch (e: IOException) {
            Timber.e(e, "Error read response")
        }
        return content
    }

    protected fun getMessageUnknown() = context.getString(R.string.error_message_unknown)
    protected fun getMessageNetwork() = context.getString(R.string.error_message_network)
}