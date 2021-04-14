package com.dicoding.bfaa.githubuser.data.network.responses

import com.google.gson.annotations.SerializedName

data class RepositoryResponse(
    @SerializedName("owner")
    val owner: UserResponse,
    @SerializedName("name")
    val name: String,
    @SerializedName("html_url")
    val url: String,
    @SerializedName("language")
    val language: String?
)
