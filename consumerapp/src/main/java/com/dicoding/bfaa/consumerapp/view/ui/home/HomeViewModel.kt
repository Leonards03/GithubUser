package com.dicoding.bfaa.consumerapp.view.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.bfaa.consumerapp.data.model.User
import com.dicoding.bfaa.consumerapp.data.repository.MainRepository
import com.dicoding.bfaa.consumerapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _favoriteList: MutableLiveData<Resource<List<User>>> = MutableLiveData()
    val favoriteList: LiveData<Resource<List<User>>> get() = _favoriteList

    suspend fun getFavoriteList(context: Context) = withContext(Dispatchers.IO) {
        _favoriteList.postValue(Resource.loading(null))
        try {
            _favoriteList.postValue(Resource.success(repository.getFavoriteList(context)))
        } catch (exception: Exception) {
            _favoriteList.postValue(
                Resource.error(
                    null,
                    message = exception.message ?: "Error occured"
                )
            )
        }
    }
}