package com.dicoding.bfaa.githubuser.data

import android.content.res.TypedArray
import com.dicoding.bfaa.githubuser.data.entity.User

class UsersData(
    usernames: Array<String>,
    names: Array<String>,
    avatars: TypedArray,
    locations: Array<String>,
    followers: Array<String>,
    followings: Array<String>
) {
    val list: ArrayList<User> = arrayListOf()

    init {
        for (index in usernames.indices) {
            val user = User(
                usernames[index],
                names[index],
                avatars.getResourceId(index, -1),
                locations[index],
                followers[index],
                followings[index]
            )
            list.add(user)
        }
    }
}