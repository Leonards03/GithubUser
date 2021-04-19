package com.dicoding.bfaa.githubuser.view.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.bfaa.githubuser.data.model.Repository
import com.dicoding.bfaa.githubuser.data.model.User
import com.dicoding.bfaa.githubuser.data.repository.MainRepository
import com.dicoding.bfaa.githubuser.di.IoDispatcher
import com.dicoding.bfaa.githubuser.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MainRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private var _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isFavorite
        get() = checkNotNull(_isFavorite.value)

    fun setFavoriteState(isFavorite: Boolean) {
        _isFavorite.value = isFavorite
    }


    fun toggleFavorite(){
        Log.d("DetailViewModel", "pre toggle: $isFavorite")
        _isFavorite.value = !isFavorite
        Log.d("DetailViewModel", "post toggle: $isFavorite")
    }

    private var _username: MutableLiveData<String> = MutableLiveData()
    val username
        get() = checkNotNull(_username.value)
    fun setUsername(username: String) {
        _username.value = username
        viewModelScope.launch {
            getUserDetails(username)
            getUserRepositories(username)
            getUserFollowers(username)
            getUserFollowing(username)
        }
    }


    private val _userDetails: MutableLiveData<Resource<User>> = MutableLiveData()
    private suspend fun getUserDetails(username: String) = withContext(ioDispatcher) {
        _userDetails.postValue(Resource.loading(null))
        try {
            if (isFavorite)
                _userDetails.postValue(
                    Resource.success(
                        repository.getUser(username)
                    )
                )
            else
                _userDetails.postValue(
                    Resource.success(
                        repository.fetchUser(username)
                    )
                )
        } catch (exception: Exception) {
            _userDetails.postValue(
                Resource.error(
                    null,
                    message = exception.message ?: "Error occured: ${exception.stackTrace}"
                )
            )
        }
    }

    val userDetails get(): LiveData<Resource<User>> = _userDetails

    private val _userFollowers: MutableLiveData<Resource<List<User>>> = MutableLiveData()
    private suspend fun getUserFollowers(username: String) = withContext(ioDispatcher) {
        _userFollowers.postValue(Resource.loading(null))
        try {
            if (isFavorite)
                _userFollowers.postValue(
                    Resource.success(
                        repository.getFollowersByUsername(username)
                    )
                )
            else
                _userFollowers.postValue(
                    Resource.success(
                        repository.fetchFollowersByUsername(username)
                    )
                )

        } catch (exception: Exception) {
            _userFollowers.postValue(
                Resource.error(
                    null,
                    message = exception.message ?: "Error occured"
                )
            )
        }
    }

    val userFollowers get(): LiveData<Resource<List<User>>> = _userFollowers

    private val _userFollowing: MutableLiveData<Resource<List<User>>> = MutableLiveData()
    private suspend fun getUserFollowing(username: String) = withContext(ioDispatcher) {
        _userFollowing.postValue(Resource.loading(null))
        try {
            if (isFavorite)
                _userFollowing.postValue(
                    Resource.success(
                        repository.getFollowingByUsername(username)
                    )
                )
            else
                _userFollowing.postValue(
                    Resource.success(
                        repository.fetchFollowingByUsername(username)
                    )
                )
        } catch (exception: Exception) {
            _userFollowing.postValue(
                Resource.error(
                    null,
                    message = exception.message ?: "Error occured"
                )
            )
        }
    }

    val userFollowing get(): LiveData<Resource<List<User>>> = _userFollowing

    private val _userRepositories: MutableLiveData<Resource<List<Repository>>> = MutableLiveData()
    private suspend fun getUserRepositories(username: String) = withContext(ioDispatcher) {
        _userRepositories.postValue(Resource.loading(null))
        try {
            if (isFavorite)
                _userRepositories.postValue(
                    Resource.success(
                        repository.getRepositoriesByUsername(username)
                    )
                )
            else
                _userRepositories.postValue(
                    Resource.success(
                        repository.fetchRepositoriesByUsername(username)
                    )
                )

        } catch (exception: Exception) {
            _userRepositories.postValue(
                Resource.error(
                    null,
                    message = exception.message ?: "Error occured"
                )
            )
        }
    }

    val userRepositories get(): LiveData<Resource<List<Repository>>> = _userRepositories

    fun addUserToFavorite() {
        val user = checkNotNull(userDetails.value?.data)
        val followers = checkNotNull(userFollowers.value?.data)
        val following = checkNotNull(userFollowing.value?.data)
        val repos = checkNotNull(userRepositories.value?.data)

        viewModelScope.launch(ioDispatcher) {
            repository.addUserToFavorite(user, followers, following, repos)
        }
    }

    fun removeUserFromFavorite() {
        val user = checkNotNull(userDetails.value?.data)

        viewModelScope.launch(ioDispatcher) {
            repository.removeUserFromFavorite(user)
        }
    }
}