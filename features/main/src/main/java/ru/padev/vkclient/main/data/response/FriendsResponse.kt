package ru.padev.vkclient.main.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.padev.vkclient.main.data.response.FriendsList

data class FriendsResponse (
    @SerializedName("response") @Expose val friendsList: FriendsList
)