package com.codingwithmitch.flowexamples

sealed class StateEvent {

    class GetObject1: StateEvent()

    class GetObject2: StateEvent()

    class GetObject3: StateEvent()
}