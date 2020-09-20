package ru.padev.vkclient.core.domain.exception

import retrofit2.HttpException
import java.io.IOException

open class DefaultErrorBundle(var exception: Exception) {

    protected val DEFAULT_ERROR_MSG = "Unknown error"

    open fun getErrorCode(): ErrorCode {
        if (exception is IOException) {
            return ErrorCode.NETWORK
        }
        if (exception is HttpException) {
            val httpException = exception as HttpException
            if (httpException.code() == 401) {
                return ErrorCode.NOT_AUTHORIZED
            }
            if (httpException.code() >= 400 && httpException.code() != 504 && httpException.code() != 401) {
                return ErrorCode.UNKNOWN
            }
            if (httpException.code() == 504) {
                return ErrorCode.NETWORK
            }
        }
        return ErrorCode.SKIP_ERROR
    }

    fun getDefaultErrorMsg(): String? {
        return DEFAULT_ERROR_MSG
    }

    fun getErrorMessage(): String? {
        return exception.message
    }

    fun isNetwork(): Boolean {
        val code = getErrorCode()
        return code != ErrorCode.SKIP_ERROR
    }
}