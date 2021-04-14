package com.dicoding.bfaa.githubuser.view.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.bfaa.githubuser.data.model.User
import com.dicoding.bfaa.githubuser.data.repository.MainRepository
import com.dicoding.bfaa.githubuser.di.IoDispatcher
import com.dicoding.bfaa.githubuser.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val username = MutableLiveData<String>()
    fun setUsername(username: String) {
        this.username.value = username
    }

    fun fetchUserDetails() = liveData(ioDispatcher) {
        emit(Resource.loading(null))
        try {
            username.value?.let {
                emit(Resource.success(mainRepository.fetchUser(it)))
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, message = exception.message ?: "Error occured"))
        }
    }

    fun fetchUserRepositories() = liveData(ioDispatcher) {
        emit(Resource.loading(null))
        try {
            val test = mainRepository.fetchRepositoriesByUsername(username.value!!)
            emit(Resource.success(test))
        } catch (exception: Exception) {
            emit(Resource.error(null, message = exception.message ?: "Error occured"))
        }
    }

    fun fetchUserFollowers() = liveData(ioDispatcher) {
        emit(Resource.loading(null))
        try {
            username.value?.let {
                emit(Resource.success(mainRepository.fetchFollowersByUsername(it)))
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, message = exception.message ?: "Error occured"))
        }
    }

    fun fetchUserFollowing() = liveData(ioDispatcher) {
        emit(Resource.loading(null))
        try {
            username.value?.let {
                emit(Resource.success(mainRepository.fetchFollowingByUsername(it)))
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, message = exception.message ?: "Error occured"))
        }
    }
}