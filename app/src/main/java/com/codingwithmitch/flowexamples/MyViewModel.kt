package com.codingwithmitch.flowexamples


import androidx.lifecycle.*
import com.codingwithmitch.flowexamples.StateEvent.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest

@ExperimentalCoroutinesApi
class MyViewModel
    constructor(
        private val repository: Repository
    ): ViewModel() {

    private val _stateEvent: MutableLiveData<StateEvent> = MutableLiveData()

    val dataState: LiveData<DataState<String>> = Transformations
        .switchMap(_stateEvent){ stateEvent ->
            handleStateEvent(stateEvent)
        }

    private fun handleStateEvent(stateEvent: StateEvent?): LiveData<DataState<String>> {

        return when(stateEvent){

            is GetObject1 -> {
                repository.getObject1().asLiveData()
            }

            is GetObject2 -> {
                repository.getObject2().asLiveData()
            }

            is GetObject3 -> {
                repository.getObject3().asLiveData()
            }
            else -> repository.getObject1().asLiveData()
        }
    }

    fun setStateEvent(stateEvent: StateEvent){
        _stateEvent.value = stateEvent
    }

}













