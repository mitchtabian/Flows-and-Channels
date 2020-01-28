package com.codingwithmitch.flowexamples


import androidx.lifecycle.*
import com.codingwithmitch.flowexamples.StateEvent.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MyViewModel
    constructor(
        private val repository: Repository
    ): ViewModel() {

    val channel = ConflatedBroadcastChannel<DataState<ViewState>>()

    private val _stateEvent: MutableLiveData<StateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    val viewState: LiveData<ViewState>
        get() = _viewState

    val dataState: LiveData<DataState<String>> = Transformations
        .switchMap(_stateEvent){ stateEvent ->
            handleStateEvent(stateEvent)
        }

    private fun offerToChannel(dataState: DataState<ViewState>){
        if(!channel.isClosedForSend){
            channel.offer(dataState)
        }
    }

    private fun handleStateEvent(stateEvent: StateEvent?): LiveData<DataState<String>> {

        return when(stateEvent){

            is GetObject1 -> {
                viewModelScope.launch {

                    repository.getObject1()
                        .map { dataState ->
                            val viewState = ViewState(object1 = dataState.data?.getContentIfNotHandled())
                            DataState(
                                data = Event(viewState),
                                errorEvent = dataState.errorEvent,
                                loading = dataState.loading

                            )
                        }
                        .collect(object: FlowCollector<DataState<ViewState>?>{

                            override suspend fun emit(value: DataState<ViewState>?) {
                                if(value != null){
                                    offerToChannel(value)
                                }
                            }
                        })
                }

                repository.getObject1().asLiveData()
            }

            is GetObject2 -> {
                viewModelScope.launch {

                    repository.getObject2()
                        .map { dataState ->

                            val viewState = ViewState(object2 = dataState.data?.getContentIfNotHandled())
                            DataState(
                                data = Event(viewState),
                                errorEvent = dataState.errorEvent,
                                loading = dataState.loading

                            )
                        }
                        .collect(object: FlowCollector<DataState<ViewState>?>{

                            override suspend fun emit(value: DataState<ViewState>?) {
                                if(value != null){
                                    offerToChannel(value)
                                }
                            }
                        })
                }
                repository.getObject2().asLiveData()
            }

            is GetObject3 -> {
                viewModelScope.launch {

                    repository.getObject3()
                        .map { dataState ->

                            val viewState = ViewState(object3 = dataState.data?.getContentIfNotHandled())
                            DataState(
                                data = Event(viewState),
                                errorEvent = dataState.errorEvent,
                                loading = dataState.loading

                            )
                        }
                        .collect(object: FlowCollector<DataState<ViewState>?>{

                            override suspend fun emit(value: DataState<ViewState>?) {
                                if(value != null){
                                    offerToChannel(value)
                                }
                            }
                        })
                }
                repository.getObject3().asLiveData()
            }
            else -> repository.getObject1().asLiveData()
        }
    }

    fun setStateEvent(stateEvent: StateEvent){
        _stateEvent.value = stateEvent
    }

    fun setViewState(viewState: ViewState){
        _viewState.value = viewState
    }

    fun setObject1(object1: String){
        val update = getCurrentViewStateOrNew()
        update.object1 = object1
        setViewState(update)
    }

    fun setObject2(object2: String){
        val update = getCurrentViewStateOrNew()
        update.object2 = object2
        setViewState(update)
    }

    fun setObject3(object3: String){
        val update = getCurrentViewStateOrNew()
        update.object3 = object3
        setViewState(update)
    }

    fun getCurrentViewStateOrNew(): ViewState{
        val value = viewState.value?.let{
            it
        }?: ViewState()
        return value
    }

    fun handleNewData(viewState: ViewState){
        when{
            viewState.object1 != null -> setObject1(viewState.object1!!)
            viewState.object2 != null -> setObject2(viewState.object2!!)
            viewState.object3 != null -> setObject3(viewState.object3!!)
        }
    }

}













