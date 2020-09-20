package ru.padev.vkclient.main.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LaunchAppResponseNotification(
    @SerializedName("id") @Expose
    val id: Int = 0,

    @SerializedName("body")
    @Expose
    val body: String,

    @SerializedName("start")
    @Expose
    val start: String,

    @SerializedName("end")
    @Expose
    val end: String
)