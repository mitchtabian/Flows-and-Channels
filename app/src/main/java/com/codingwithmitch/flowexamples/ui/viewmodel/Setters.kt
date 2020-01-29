package com.codingwithmitch.flowexamples.ui.viewmodel

import com.codingwithmitch.flowexamples.ui.state.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi



@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MyViewModel.setObject1(object1: String){
    val update = getCurrentViewStateOrNew()
    update.object1 = object1
    setViewState(update)
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MyViewModel.setObject2(object2: String){
    val update = getCurrentViewStateOrNew()
    update.object2 = object2
    setViewState(update)
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MyViewModel.setObject3(object3: String){
    val update = getCurrentViewStateOrNew()
    update.object3 = object3
    setViewState(update)
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MyViewModel.clearActiveJobCounter(){
    val update = getCurrentViewStateOrNew()
    update.activeJobCounter.clear()
    setViewState(update)
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MyViewModel.addJobToCounter(stateEventName: String){
    val update = getCurrentViewStateOrNew()
    update.activeJobCounter.add(stateEventName)
    setViewState(update)
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MyViewModel.removeJobFromCounter(stateEventName: String){
    val update = getCurrentViewStateOrNew()
    update.activeJobCounter.remove(stateEventName)
    setViewState(update)
}










