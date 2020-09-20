package ru.padev.vkclient.main.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.padev.vkclient.main.data.response.Friend

data class FriendsList (
    @SerializedName("items") @Expose val friends: List<Friend>
)