package com.dicoding.bfaa.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dicoding.bfaa.githubuser.data.entity.User
import com.dicoding.bfaa.githubuser.repository.Repository

class MainViewModel(application: Application) : AndroidViewModel(
    application
) {
    private val repository: Repository = Repository(application)

    fun getUsers(): ArrayList<User> {
        return repository.getUsers()
    }
}