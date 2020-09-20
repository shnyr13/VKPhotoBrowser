package ru.padev.vkclient.core.network.response

import com.google.gson.annotations.SerializedName

data class ErrorResponseWithMessage(
    @SerializedName("message")
    val message: String
) : ErrorResponse()