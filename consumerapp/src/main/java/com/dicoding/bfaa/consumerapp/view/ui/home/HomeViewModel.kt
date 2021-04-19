package com.dicoding.bfaa.consumerapp.view.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.bfaa.consumerapp.data.repository.MainRepository
import com.dicoding.bfaa.consumerapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    fun getFavoriteList(context: Context) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getFavoriteList(context)))
        } catch (exception: Exception) {
            emit(Resource.error(null, message = exception.message ?: "Error occured"))
        }
    }
}