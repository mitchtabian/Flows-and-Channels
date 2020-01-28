package com.codingwithmitch.flowexamples.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codingwithmitch.flowexamples.repository.Repository
import com.codingwithmitch.flowexamples.ui.viewmodel.MyViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class MyViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = MyViewModel(
        repository
    ) as T
}