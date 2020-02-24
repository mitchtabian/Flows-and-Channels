package com.codingwithmitch.flowexamples.util

import com.codingwithmitch.flowexamples.ui.state.StateEvent

const val UNKNOWN_ERROR = "Unknown Error"

data class DataState<T>(
    var error: ErrorState? = null,
    var data: T? = null,
    var stateEvent: StateEvent
) {

    companion object {

        fun <T> error(
            errorMessage: String = UNKNOWN_ERROR,
            stateEvent: StateEvent
        ): DataState<T> {
            return DataState(
                error = ErrorState(errorMessage),
                data = null,
                stateEvent = stateEvent
            )
        }

        fun <T> data(
            data: T? = null,
            stateEvent: StateEvent
        ): DataState<T> {
            return DataState(
                error = null,
                data = data,
                stateEvent = stateEvent
            )
        }
    }
}


