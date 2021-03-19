package com.dicoding.bfaa.githubuser.data

data class User(
    val username: String,
    val name: String,
    val avatar: Int,
    val follower: Int, // arrayList?
    val following: Int, // arrayList?
    val location: String,
    val repository: Int // arrayList?
)

// few items are subject to change because of the possibility i could made