package com.dicoding.bfaa.githubuser.data.model

data class User(
    val username: String,
    val name: String?,
    val bio: String?,
    val avatar: String,
    val location: String?,
    val company: String?,
    val followersCount: Int?,
    val followingCount: Int?
) {
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
