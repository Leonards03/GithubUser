package com.dicoding.bfaa.githubuser.view.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.bfaa.githubuser.data.repository.MainRepository
import com.dicoding.bfaa.githubuser.di.IoDispatcher
import com.dicoding.bfaa.githubuser.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _favoriteUser = liveData(ioDispatcher) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.getFavoriteList()))
        } catch (exception: Exception) {
            emit(Resource.error(null, message = exception.message ?: "Error occured"))
        }
    }
    val favoriteUser get() = _favoriteUser
}