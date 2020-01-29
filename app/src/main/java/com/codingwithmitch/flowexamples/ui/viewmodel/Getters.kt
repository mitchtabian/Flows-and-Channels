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

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MyViewModel.areAnyJobsActive(): Boolean{
    val viewState = getCurrentViewStateOrNew()
    return viewState.activeJobCounter.size > 0
}


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MyViewModel.getNumActiveJobs(): Int {
    val viewState = getCurrentViewStateOrNew()
    return viewState.activeJobCounter.size
}


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MyViewModel.isJobAlreadyActive(stateEventName: String): Boolean {
    val viewState = getCurrentViewStateOrNew()
    return viewState.activeJobCounter.contains(stateEventName)
}





















