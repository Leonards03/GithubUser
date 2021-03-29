package com.dicoding.bfaa.githubuser.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String,
    val name: String,
    val avatar: Int,
    val location: String,
    val company: String,
    val repository: String,
    val follower: String,
    val following: String
) : Parcelable
