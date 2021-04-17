package com.dicoding.bfaa.consumerapp.data.repository

import com.dicoding.bfaa.consumerapp.data.local.LocalDataSource
import com.dicoding.bfaa.consumerapp.data.mapper.RepositoryMapper
import com.dicoding.bfaa.consumerapp.data.mapper.UserMapper
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val userMapper: UserMapper,
    private val repositoryMapper: RepositoryMapper
) {
}