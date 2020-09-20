package ru.padev.vkclient.main.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserDetailsResponse (
    @SerializedName("response") @Expose val users: List<UserDetails>
)