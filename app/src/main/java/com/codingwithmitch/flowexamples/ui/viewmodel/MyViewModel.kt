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
                launchJob(GetObject1(), repository.getObject1())
            }

            is GetObject2 -> {
                launchJob(GetObject2(), repository.getObject2())
            }

            is GetObject3 -> {
                launchJob(GetObject3(), repository.getObject3())
            }
        }
    }

    fun launchJob(stateEvent: StateEvent, jobFunction: Flow<DataState<ViewState>> ){
        if(!isJobAlreadyActive(stateEvent.toString())){
            addJobToCounter(stateEvent.toString())
            jobFunction
                .onEach { dataState ->
                    offerToDataChannel(dataState)
                }
                .launchIn(viewModelScope)
        }
    }


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













