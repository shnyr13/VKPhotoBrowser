package ru.padev.vkclient.main.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Country(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("title") @Expose val title: String
)