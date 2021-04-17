package com.dicoding.bfaa.consumerapp.view.ui.detail

import com.dicoding.bfaa.consumerapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MainRepository
) {

}