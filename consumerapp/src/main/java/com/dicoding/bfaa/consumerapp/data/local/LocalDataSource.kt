package com.dicoding.bfaa.consumerapp.data.local

import com.dicoding.bfaa.consumerapp.data.local.dao.RepositoryDao
import com.dicoding.bfaa.consumerapp.data.local.dao.UserDao
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val userDao: UserDao,
    private val repositoryDao: RepositoryDao
) {
}