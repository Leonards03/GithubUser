package com.dicoding.bfaa.githubuser.data.repository

import android.app.Application
import com.dicoding.bfaa.githubuser.R
import com.dicoding.bfaa.githubuser.data.entity.User

class Repository(private val application: Application) {
    private var userList = arrayListOf<User>()

    fun getUsers(): ArrayList<User> {
        if (userList.isEmpty()) {
            with(application.resources) {
                val usernames = getStringArray(R.array.username)
                val names = getStringArray(R.array.name)
                val avatars = obtainTypedArray(R.array.avatar)
                val locations = getStringArray(R.array.location)
                val companies = getStringArray(R.array.company)
                val repository = getStringArray(R.array.repository)
                val follower = getStringArray(R.array.followers)
                val following = getStringArray(R.array.following)

                for (index in usernames.indices) {
                    val user = User(
                        usernames[index],
                        names[index],
                        avatars.getResourceId(index, -1),
                        locations[index],
                        companies[index],
                        repository[index],
                        follower[index],
                        following[index]
                    )

                    userList.add(user)
                }

                avatars.recycle()
            }
        }
        return userList
    }

}