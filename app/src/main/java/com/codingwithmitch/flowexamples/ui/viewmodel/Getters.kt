package com.codingwithmitch.flowexamples.ui.viewmodel

import com.codingwithmitch.flowexamples.ui.state.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MyViewModel.getCurrentViewStateOrNew(): ViewState {
    val value = viewState.value?.let{
        it
    }?: ViewState()
    return value
}













