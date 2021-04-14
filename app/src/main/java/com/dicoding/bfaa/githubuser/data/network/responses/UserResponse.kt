package com.dicoding.bfaa.githubuser.data.network.responses

import com.dicoding.bfaa.githubuser.data.model.Repository
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("login")
    val username: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("avatar_url")
    val avatar: String,
    @SerializedName("location")
    val location: String?,
    @SerializedName("company")
    val company: String?,
    @SerializedName("followers")
    val followers: Int?,
    @SerializedName("following")
    val following: Int?
){
    constructor(username: String, avatar: String) : this(
        username,
        null,
        null,
        avatar,
        null,
        null,
        null,
        null
    )
}
