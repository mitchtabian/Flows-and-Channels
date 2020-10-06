package com.codingwithmitch.flowexamples.ui.viewmodel


import android.util.Log
import androidx.lifecycle.*
import com.codingwithmitch.flowexamples.util.DataState
import com.codingwithmitch.flowexamples.ui.state.StateEvent
import com.codingwithmitch.flowexamples.ui.state.StateEvent.*
import com.codingwithmitch.flowexamples.ui.state.ViewState
import com.codingwithmitch.flowexamples.repository.Repository
import com.codingwithmitch.flowexamples.util.ErrorStack
import com.codingwithmitch.flowexamples.util.ErrorState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@UseExperimental(FlowPreview::class)
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MyViewModel
constructor(
    private val repository: Repository
): ViewModel() {

    private val TAG: String = "AppDebug"

    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    val viewState: LiveData<ViewState>
        get() = _viewState

    val errorStack = ErrorStack()

    val errorState: LiveData<ErrorState> = errorStack.errorState

    fun setStateEvent(stateEvent: StateEvent){

        when(stateEvent){
            is GetObject1 -> {
                launchJob(GetObject1(), repository.getObject1(GetObject1()))
            }

            is GetObject2 -> {
                launchJob(GetObject2(), repository.getObject2(GetObject2()))
            }

            is GetObject3 -> {
                launchJob(GetObject3(), repository.getObject3(GetObject3()))
            }
        }
    }

    fun launchJob(stateEvent: StateEvent, jobFunction: Flow<DataState<ViewState>> ){
        if(!isJobAlreadyActive(stateEvent.toString())){
            addJobToCounter(stateEvent.toString())
            jobFunction
                .onEach{ dataState ->
                    dataState.data?.let { data ->
                        handleNewData(dataState.stateEvent, data)
                    }
                    dataState.error?.let { error ->
                        handleNewError(dataState.stateEvent, error)
                    }
                }
                .launchIn(viewModelScope )
        }
    }

    private fun handleNewError(stateEvent: StateEvent, error: ErrorState) {
        appendErrorState(error)
        removeJobFromCounter(stateEvent.toString())
    }

    fun handleNewData(stateEvent: StateEvent, viewState: ViewState){
        when{
            viewState.object1 != null -> {
                setObject1(viewState.object1!!)
            }
            viewState.object2 != null -> {
                setObject2(viewState.object2!!)
            }
            viewState.object3 != null -> {
                setObject3(viewState.object3!!)
            }
        }
        removeJobFromCounter(stateEvent.toString())
    }

    fun setViewState(viewState: ViewState){
        _viewState.value = viewState
    }

}













