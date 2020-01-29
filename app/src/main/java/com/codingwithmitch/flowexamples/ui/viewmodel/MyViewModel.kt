package com.codingwithmitch.flowexamples.ui.viewmodel


import android.util.Log
import androidx.lifecycle.*
import com.codingwithmitch.flowexamples.util.DataState
import com.codingwithmitch.flowexamples.ui.state.StateEvent
import com.codingwithmitch.flowexamples.ui.state.StateEvent.*
import com.codingwithmitch.flowexamples.ui.state.ViewState
import com.codingwithmitch.flowexamples.repository.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

@UseExperimental(FlowPreview::class)
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MyViewModel
constructor(
    private val repository: Repository
): ViewModel() {

    private val TAG: String = "AppDebug"

    private val dataChannel = ConflatedBroadcastChannel<DataState<ViewState>>()

    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    val viewState: LiveData<ViewState>
        get() = _viewState


    init {
        setupChannel()
    }

    private fun setupChannel(){
        dataChannel
            .asFlow()
            .onEach{ dataState ->
                Log.d(TAG, "MyViewModel: emit: ${dataState}")
                dataState.dataEvent?.getContentIfNotHandled()?.let { data ->
                    handleNewData(data)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun offerToDataChannel(dataState: DataState<ViewState>){
        if(!dataChannel.isClosedForSend){
            dataChannel.offer(dataState)
        }
    }

    fun setStateEvent(stateEvent: StateEvent){

        when(stateEvent){
            is GetObject1 -> {
                if(!isJobAlreadyActive(GetObject1().toString())){
                    addJobToCounter(GetObject1().toString())
                    repository.getObject1()
                        .onEach { dataState ->
                            offerToDataChannel(dataState)
                        }
                        .launchIn(viewModelScope)
                }
            }

            is GetObject2 -> {
                if(!isJobAlreadyActive(GetObject2().toString())){
                    addJobToCounter(GetObject2().toString())
                    repository.getObject2()
                        .onEach { dataState ->
                            offerToDataChannel(dataState)
                        }
                        .launchIn(viewModelScope)
                }
            }

            is GetObject3 -> {
                if(!isJobAlreadyActive(GetObject3().toString())){
                    addJobToCounter(GetObject3().toString())
                    repository.getObject3()
                        .onEach { dataState ->
                            offerToDataChannel(dataState)
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

//    fun launchJob(jobFunction: suspend -> (), ){
//
//    }


    fun handleNewData(viewState: ViewState){
        when{
            viewState.object1 != null -> {
                removeJobFromCounter(GetObject1().toString())
                setObject1(viewState.object1!!)
            }
            viewState.object2 != null -> {
                removeJobFromCounter(GetObject2().toString())
                setObject2(viewState.object2!!)
            }
            viewState.object3 != null -> {
                removeJobFromCounter(GetObject3().toString())
                setObject3(viewState.object3!!)
            }
        }
    }

    fun setViewState(viewState: ViewState){
        _viewState.value = viewState
    }

}













