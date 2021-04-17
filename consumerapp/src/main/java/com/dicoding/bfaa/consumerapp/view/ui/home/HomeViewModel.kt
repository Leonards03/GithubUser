package com.dicoding.bfaa.consumerapp.view.ui.home

import androidx.lifecycle.ViewModel
import com.dicoding.bfaa.consumerapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

}