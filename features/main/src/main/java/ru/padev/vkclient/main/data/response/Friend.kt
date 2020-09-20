package ru.padev.vkclient.main.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Friend (
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("first_name") @Expose val firstName: String,
    @SerializedName("last_name") @Expose val lastName: String,
    @SerializedName("photo_100") @Expose val photo100: String?,
    @SerializedName("photo_id") @Expose val photoId: String?,
    @SerializedName("online") @Expose val online: Int
)