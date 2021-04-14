package com.dicoding.bfaa.githubuser.view.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.bfaa.githubuser.data.repository.MainRepository
import com.dicoding.bfaa.githubuser.di.IoDispatcher
import com.dicoding.bfaa.githubuser.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val mutableQuery = MutableLiveData<String>()
    val query: MutableLiveData<String> get() = mutableQuery

    fun passQuery(query: String) {
        mutableQuery.value = query
    }

    fun searchUser(query: String) = liveData(ioDispatcher) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.searchUser(query)))
        } catch (exception: Exception) {
            emit(Resource.error(null, message = exception.message ?: "Error occured"))
        }
    }
}