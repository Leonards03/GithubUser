package com.dicoding.bfaa.consumerapp.view.ui.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.bfaa.consumerapp.data.model.Repository
import com.dicoding.bfaa.consumerapp.data.model.User
import com.dicoding.bfaa.consumerapp.data.repository.MainRepository
import com.dicoding.bfaa.consumerapp.di.IoDispatcher
import com.dicoding.bfaa.consumerapp.utils.Resource
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

    fun prepare(username: String, context: Context) {
        try {
            viewModelScope.launch {
                getUserDetails(username, context)
                getUserRepositories(username, context)
                getUserFollowers(username, context)
                getUserFollowing(username, context)
            }
        } catch (ex: Exception) {
            Log.e("DetailViewModel", ex.message ?: "$ex")
        }
    }

    private val _userDetails: MutableLiveData<Resource<User>> = MutableLiveData()
    val userDetails get(): LiveData<Resource<User>> = _userDetails
    private suspend fun getUserDetails(username: String, context: Context) =
        withContext(ioDispatcher) {
            _userDetails.postValue(Resource.loading(null))
            try {
                _userDetails.postValue(Resource.success(repository.getUser(username, context)))
            } catch (exception: Exception) {
                _userDetails.postValue(
                    Resource.error(
                        null,
                        message = exception.message ?: "Error occured: $exception"
                    )
                )
            }
        }


    private val _userFollowers: MutableLiveData<Resource<List<User>>> = MutableLiveData()
    val userFollowers get(): LiveData<Resource<List<User>>> = _userFollowers
    private suspend fun getUserFollowers(username: String, context: Context) =
        withContext(ioDispatcher) {
            _userFollowers.postValue(Resource.loading(null))
            try {
                _userFollowers.postValue(
                    Resource.success(
                        repository.getFollowers(
                            username,
                            context
                        )
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


    private val _userFollowing: MutableLiveData<Resource<List<User>>> = MutableLiveData()
    val userFollowing get(): LiveData<Resource<List<User>>> = _userFollowing
    private suspend fun getUserFollowing(username: String, context: Context) =
        withContext(ioDispatcher) {
            _userFollowing.postValue(Resource.loading(null))
            try {
                _userFollowing.postValue(
                    Resource.success(
                        repository.getFollowing(
                            username,
                            context
                        )
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


    private val _userRepositories: MutableLiveData<Resource<List<Repository>>> = MutableLiveData()
    val userRepositories get(): LiveData<Resource<List<Repository>>> = _userRepositories
    private suspend fun getUserRepositories(username: String, context: Context) =
        withContext(ioDispatcher) {
            _userRepositories.postValue(Resource.loading(null))
            try {
                _userRepositories.postValue(
                    Resource.success(
                        repository.getRepositories(username, context)
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

    fun removeUserFromFavorite(username: String, context: Context) =
        repository.removeUserFromFavorite(username, context)


}