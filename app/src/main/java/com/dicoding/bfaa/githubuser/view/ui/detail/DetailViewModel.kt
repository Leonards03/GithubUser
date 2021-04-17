package com.dicoding.bfaa.githubuser.view.ui.detail

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
    private val mainRepository: MainRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    var loadFromNetwork: Boolean = true

    private val username = MutableLiveData<String>()

    fun setUsername(username: String) {
        this.username.value = username
        viewModelScope.launch {
            getUserDetails()
            getUserFollowers()
            getUserFollowing()
            getUserRepositories()
        }
    }

    private val _userDetails: MutableLiveData<Resource<User>> = MutableLiveData()
    private suspend fun getUserDetails() = withContext(ioDispatcher) {
        _userDetails.postValue(Resource.loading(null))
        try {
            username.value?.let {
                if (loadFromNetwork) {
                    _userDetails.postValue(Resource.success(mainRepository.fetchUser(it)))
                } else {
                    _userDetails.postValue(Resource.success(mainRepository.getUser(it)))
                }
            }
        } catch (exception: Exception) {
            _userDetails.postValue(
                Resource.error(
                    null,
                    message = exception.message ?: "Error occured"
                )
            )
        }
    }

    val userDetails get() = _userDetails

    private val _userFollowers: MutableLiveData<Resource<List<User>>> = MutableLiveData()
    private suspend fun getUserFollowers() = withContext(ioDispatcher) {
        _userFollowers.postValue(Resource.loading(null))
        try {
            username.value?.let {
                if (loadFromNetwork)
                    _userFollowers.postValue(
                        Resource.success(
                            mainRepository.fetchFollowersByUsername(
                                it
                            )
                        )
                    )
                else
                    _userFollowers.postValue(
                        Resource.success(
                            mainRepository.getFollowersByUsername(
                                it
                            )
                        )
                    )
            }
        } catch (exception: Exception) {
            _userFollowers.postValue(
                Resource.error(
                    null,
                    message = exception.message ?: "Error occured"
                )
            )
        }
    }

    val userFollowers get() = _userFollowers

    private val _userFollowing: MutableLiveData<Resource<List<User>>> = MutableLiveData()
    private suspend fun getUserFollowing() = withContext(ioDispatcher) {
        _userFollowing.postValue(Resource.loading(null))
        try {
            username.value?.let {
                if (loadFromNetwork)
                    _userFollowing.postValue(
                        Resource.success(
                            mainRepository.fetchFollowingByUsername(
                                it
                            )
                        )
                    )
                else
                    _userFollowing.postValue(
                        Resource.success(
                            mainRepository.getFollowingByUsername(
                                it
                            )
                        )
                    )
            }
        } catch (exception: Exception) {
            _userFollowing.postValue(
                Resource.error(
                    null,
                    message = exception.message ?: "Error occured"
                )
            )
        }
    }

    val userFollowing get() = _userFollowing

    private val _userRepositories: MutableLiveData<Resource<List<Repository>>> = MutableLiveData()
    private suspend fun getUserRepositories() = withContext(ioDispatcher) {
        _userRepositories.postValue(Resource.loading(null))
        try {
            username.value?.let {
                if (loadFromNetwork)
                    _userRepositories.postValue(
                        Resource.success(
                            mainRepository.fetchRepositoriesByUsername(
                                it
                            )
                        )
                    )
                else
                    _userRepositories.postValue(
                        Resource.success(
                            mainRepository.getRepositoriesByUsername(
                                it
                            )
                        )
                    )
            }
        } catch (exception: Exception) {
            _userRepositories.postValue(
                Resource.error(
                    null,
                    message = exception.message ?: "Error occured"
                )
            )
        }
    }

    val userRepositories get() = _userRepositories

    fun addUserToFavorite() {
        val user = userDetails.value?.data!!
        val followers = userFollowers.value?.data!!
        val following = userFollowing.value?.data!!
        val repos = userRepositories.value?.data!!

        viewModelScope.launch(ioDispatcher) {
            mainRepository.addUserToFavorite(user, followers, following, repos)
        }
    }

    fun removeUserFromFavorite() {
        val user = userDetails.value?.data!!

        viewModelScope.launch(ioDispatcher) {
            mainRepository.removeUserFromFavorite(user)
        }
    }
}