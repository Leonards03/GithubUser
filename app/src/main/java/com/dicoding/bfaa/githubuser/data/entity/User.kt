package com.dicoding.bfaa.githubuser.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String,
    val name: String,
    val avatar: Int,
    val location: String,
    val follower: String, // arrayList?
    val following: String // arrayList?
) : Parcelable
