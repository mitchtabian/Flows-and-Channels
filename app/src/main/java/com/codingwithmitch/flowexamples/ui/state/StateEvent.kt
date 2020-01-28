package com.codingwithmitch.flowexamples.ui.state

sealed class StateEvent {

    class GetObject1: StateEvent()

    class GetObject2: StateEvent()

    class GetObject3: StateEvent()
}