package ru.padev.vkclient.main.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserDetails (
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("first_name") @Expose val firstName: String,
    @SerializedName("last_name") @Expose val lastName: String,
    @SerializedName("photo_max") @Expose val photoMax: String?,
    @SerializedName("photo_id") @Expose val photoId: String?,
    @SerializedName("city") @Expose val city: City?,
    @SerializedName("country") @Expose val country: Country?,
    @SerializedName("online") @Expose val online: Int
)