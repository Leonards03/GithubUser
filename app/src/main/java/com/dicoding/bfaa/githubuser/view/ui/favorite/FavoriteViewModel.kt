package com.dicoding.bfaa.githubuser.view.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class FavoriteViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    init {
        viewModelScope.launch {
            loadList()
        }
    }

    private val _favoriteUser: MutableLiveData<Resource<List<User>>> = MutableLiveData()
    val favoriteUser get(): LiveData<Resource<List<User>>> = _favoriteUser

    suspend fun loadList() = withContext(ioDispatcher) {
        _favoriteUser.postValue(Resource.loading(null))
        try {
            _favoriteUser.postValue(Resource.success(mainRepository.getFavoriteList()))
        } catch (exception: Exception) {
            _favoriteUser.postValue(
                Resource.error(
                    null,
                    message = exception.message ?: "Error occured"
                )
            )
        }
    }
}