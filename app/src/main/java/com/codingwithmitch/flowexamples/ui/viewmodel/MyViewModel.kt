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

    private val _activeRequestCounter: MutableLiveData<Int> = MutableLiveData(0)

    val activeRequestCounter: LiveData<Int>
        get() = _activeRequestCounter

    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    val viewState: LiveData<ViewState>
        get() = _viewState


    init {
        setupChannel()
    }

    private fun setupChannel(){
        viewModelScope.launch{
            dataChannel
                .asFlow()
                .collect{ dataState ->
                    Log.d(TAG, "MyViewModel: emit: ${dataState}")

                    dataState.dataEvent?.getContentIfNotHandled()?.let { data ->
                        handleNewData(data)
                        decrementActiveJobCounter()
                    }
                }
        }

    }

    private fun offerToDataChannel(dataState: DataState<ViewState>){
        if(!dataChannel.isClosedForSend){
            dataChannel.offer(dataState)
        }
    }

    fun setStateEvent(stateEvent: StateEvent){

        when(stateEvent){
            is GetObject1 -> {
                viewModelScope.launch {
                    
                    incrementActiveJobCounter()

                    repository.getObject1()
                        .collect{ dataState ->
                            offerToDataChannel(dataState)
                        }
                }
            }

            is GetObject2 -> {
                viewModelScope.launch {

                    incrementActiveJobCounter()

                    repository.getObject2()
                        .collect{ dataState ->
                            offerToDataChannel(dataState)
                        }
                }
            }

            is GetObject3 -> {

                incrementActiveJobCounter()

                viewModelScope.launch {

                    repository.getObject3()
                        .collect{ dataState ->
                            offerToDataChannel(dataState)
                        }
                }
            }
        }
    }

    private fun incrementActiveJobCounter(){
        var counter = _activeRequestCounter.value
        if(counter != null){
            counter++
        }
        _activeRequestCounter.value = counter
    }

    private fun decrementActiveJobCounter(){
        var counter = _activeRequestCounter.value
        if(counter != null) {
            counter--
            if(counter < 0){
                counter = 0
            }
        }
        _activeRequestCounter.value = counter
    }


    fun handleNewData(viewState: ViewState){
        when{
            viewState.object1 != null -> setObject1(viewState.object1!!)
            viewState.object2 != null -> setObject2(viewState.object2!!)
            viewState.object3 != null -> setObject3(viewState.object3!!)
        }
    }


    fun setViewState(viewState: ViewState){
        _viewState.value = viewState
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}













